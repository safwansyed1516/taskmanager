
package com.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.app.dto.TaskDto;
import com.app.entity.Task;
import com.app.exception.ErrorCodes;
import com.app.exception.TaskManagerServiceException;
import com.app.repository.TaskRepository;
import com.app.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public Task findByTaskId(Long taskId) {
		Optional<Task> task = taskRepository.findById(taskId);
		if (task.isPresent()) {
			return task.get();
		}
		return new Task();
	}

	@Override
	public TaskDto saveOrUpdateTask(Task taskEntity) throws TaskManagerServiceException {
		try {
			TaskDto taskDto = new TaskDto();
			Task savedTask = taskRepository.save(taskEntity);
			BeanUtils.copyProperties(savedTask, taskDto);
			return taskDto;

		} catch (final Exception ex) {
			log.error("Exception at save task : ", ex);
			throw ErrorCodes.FAILED_TO_SAVE_TASK.createTaskManagerServiceException();
		}
	}

	@Override
	public String deleteTask(Long taskId) throws TaskManagerServiceException {
		try {
			int delete = taskRepository.deleteTask(taskId);
			if (delete > 0) {
				return ErrorCodes.TASK_DELETED.getMessage();
			} else {
				return ErrorCodes.FAILED_TO_DELETE_TASK.getMessage();
			}
		} catch (Exception ex) {
			log.error("Excepton at delete task", ex);
			throw ErrorCodes.FAILED_TO_DELETE_TASK.createTaskManagerServiceException();
		}

	}

	@Override
	public List<TaskDto> getAllTask(int pageNo, int pageSize) throws TaskManagerServiceException {

		try {
			PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("taskId").descending());
			List<TaskDto> taskDtoList = new ArrayList<>();
			List<Task> savedTask = taskRepository.findAllByPagination(pageRequest);
			BeanUtils.copyProperties(savedTask, taskDtoList);
			return taskDtoList;

		} catch (final Exception ex) {
			log.error("Exception at get all task : ", ex);
			throw ErrorCodes.FAILED_TO_GET_TASK.createTaskManagerServiceException();
		}

	}

	@Override
	public TaskDto getTask(Long taskId) throws TaskManagerServiceException {

		try {
			TaskDto taskDto = new TaskDto();
			Task savedTask = taskRepository.findById(taskId).get();
			BeanUtils.copyProperties(savedTask, taskDto);
			return taskDto;

		} catch (final Exception ex) {
			log.error("Exception at get task : ", ex);
			throw ErrorCodes.FAILED_TO_GET_TASK.createTaskManagerServiceException();
		}

	}

	@Override
	public List<TaskDto> getAllIncompleteTask(int pageNo, int pageSize) throws TaskManagerServiceException {

		try {
			PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("taskId").descending());
			List<TaskDto> taskDtoList = new ArrayList<>();
			List<Task> savedTask = taskRepository.findByIsComplete(Boolean.FALSE, pageRequest);
			BeanUtils.copyProperties(savedTask, taskDtoList);
			return taskDtoList;

		} catch (final Exception ex) {
			log.error("Exception at get all task : ", ex);
			throw ErrorCodes.FAILED_TO_GET_TASK.createTaskManagerServiceException();
		}

	}

	@Override
	public String markTask(Long taskId, Boolean status) throws TaskManagerServiceException {

		try {
			TaskDto taskDto = new TaskDto();
			int updateStatus = taskRepository.updateStatus(taskId, status);
			if (updateStatus > 0) {
				return ErrorCodes.STATUS_UPDATED.getMessage();
			} else {
				return ErrorCodes.FAILED_TO_UPDATE_STATUS.getMessage();
			}

		} catch (final Exception ex) {
			log.error("Exception at mark task : ", ex);
			throw ErrorCodes.FAILED_TO_UPDATE_STATUS.createTaskManagerServiceException();
		}

	}

}
