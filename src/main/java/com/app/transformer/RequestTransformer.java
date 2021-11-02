package com.app.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.dto.TaskDto;
import com.app.dto.UserDto;
import com.app.entity.Task;
import com.app.entity.User;
import com.app.service.TaskService;
import com.app.service.UserInformationService;

@Component
public class RequestTransformer {

	@Autowired
	UserInformationService userInformationService;

	@Autowired
	TaskService taskService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public User transformUserEntity(UserDto userDto) {
		User user = null;
		if (userDto.getUserId() == null) {
			user = new User();
		} else {
			user = userInformationService.getUserByUserId(userDto.getUserId());
		}
		BeanUtils.copyProperties(userDto, user);
		user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		user.setIsDeleted(Boolean.FALSE);
		return user;
	}

	public Task transformTaskEntity(TaskDto taskDto) {
		Task task = null;
		if (taskDto.getTaskId() != null) {
			task = taskService.findByTaskId(taskDto.getTaskId());
		} else {
			task = new Task();
		}
		BeanUtils.copyProperties(taskDto, task);
		task.setIsDeleted(Boolean.FALSE);
		return task;
	}

}
