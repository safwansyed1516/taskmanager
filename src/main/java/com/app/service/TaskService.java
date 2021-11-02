package com.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.dto.TaskDto;
import com.app.entity.Task;
import com.app.exception.TaskManagerServiceException;

@Service
public interface TaskService {

	Task findByTaskId(Long taskId);

	TaskDto saveOrUpdateTask(Task taskEntity) throws TaskManagerServiceException;

	String deleteTask(Long taskId) throws TaskManagerServiceException;

	List<TaskDto> getAllTask(int pageNo, int pageSize) throws TaskManagerServiceException;

	TaskDto getTask(Long taskId) throws TaskManagerServiceException;

	List<TaskDto> getAllIncompleteTask(int pageNo, int pageSize) throws TaskManagerServiceException;

	String markTask(Long taskId, Boolean status) throws TaskManagerServiceException;

}
