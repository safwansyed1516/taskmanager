package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.TaskDto;
import com.app.dto.TaskManagerResponse;
import com.app.entity.Task;
import com.app.exception.ErrorCodes;
import com.app.exception.TaskManagerServiceException;
import com.app.service.TaskService;
import com.app.transformer.RequestTransformer;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/task")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {

	@Autowired
	RequestTransformer requestTransformer;

	@Autowired
	TaskService taskService;

	@ApiOperation("Endpoint for adding task")
	@PostMapping("/add")
	public ResponseEntity<TaskManagerResponse<TaskDto>> addTask(@RequestBody TaskDto taskDto)
			throws TaskManagerServiceException {
		TaskManagerResponse<TaskDto> userResponse = new TaskManagerResponse<>();
		Task taskEntity = requestTransformer.transformTaskEntity(taskDto);
		TaskDto savedUser = taskService.saveOrUpdateTask(taskEntity);
		userResponse.setHttpStatus(HttpStatus.OK);
		userResponse.setMessage(ErrorCodes.TASK_SAVED.getMessage());
		userResponse.setData(savedUser);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);

	}

	@ApiOperation("Endpoint for updating task")
	@PutMapping("/update")
	public ResponseEntity<TaskManagerResponse<TaskDto>> updateTask(@RequestBody TaskDto taskDto)
			throws TaskManagerServiceException {
		TaskManagerResponse<TaskDto> userResponse = new TaskManagerResponse<>();
		Task taskEntity = requestTransformer.transformTaskEntity(taskDto);
		TaskDto savedUser = taskService.saveOrUpdateTask(taskEntity);
		userResponse.setHttpStatus(HttpStatus.OK);
		userResponse.setMessage(ErrorCodes.TASK_UPDATED.getMessage());
		userResponse.setData(savedUser);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);

	}

	@ApiOperation("Endpoint for deleting task")
	@DeleteMapping("/delete/{taskId}")
	public ResponseEntity<TaskManagerResponse<String>> deleteTask(@PathVariable Long taskId)
			throws TaskManagerServiceException {
		TaskManagerResponse<String> userResponse = new TaskManagerResponse<>();
		String deletedUser = taskService.deleteTask(taskId);
		userResponse.setHttpStatus(HttpStatus.OK);
		userResponse.setMessage(ErrorCodes.TASK_DELETED.getMessage());
		userResponse.setData(deletedUser);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);

	}

	@ApiOperation("Endpoint for getting single task")
	@GetMapping("/get/{taskId}")
	public ResponseEntity<TaskManagerResponse<TaskDto>> getTask(@PathVariable Long taskId)
			throws TaskManagerServiceException {
		TaskManagerResponse<TaskDto> userResponse = new TaskManagerResponse<>();
		TaskDto task = taskService.getTask(taskId);
		userResponse.setHttpStatus(HttpStatus.OK);
		userResponse.setMessage(ErrorCodes.TASK_FETCHED.getMessage());
		userResponse.setData(task);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);

	}

	@ApiOperation("Endpoint for getting all task")
	@GetMapping("/get-all/{pageNo}/{pageSize}")
	public ResponseEntity<TaskManagerResponse<List<TaskDto>>> getAllTask(@PathVariable int pageNo,
			@PathVariable int pageSize) throws TaskManagerServiceException {
		TaskManagerResponse<List<TaskDto>> userResponse = new TaskManagerResponse<>();
		List<TaskDto> taskList = taskService.getAllTask(pageNo, pageSize);
		userResponse.setHttpStatus(HttpStatus.OK);
		userResponse.setMessage(ErrorCodes.TASK_FETCHED.getMessage());
		userResponse.setData(taskList);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);

	}
}
