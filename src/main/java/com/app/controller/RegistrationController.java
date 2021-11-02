package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.TaskManagerResponse;
import com.app.dto.UserDto;
import com.app.entity.User;
import com.app.exception.ErrorCodes;
import com.app.exception.TaskManagerServiceException;
import com.app.service.UserInformationService;
import com.app.transformer.RequestTransformer;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/register")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegistrationController {

	@Autowired
	UserInformationService userInformationService;

	@Autowired
	RequestTransformer requestTransformer;

	@ApiOperation("Endpoint for saving user")
	@PostMapping("/user")
	public ResponseEntity<TaskManagerResponse<UserDto>> saveUser(@RequestBody UserDto userDto)
			throws TaskManagerServiceException {
		TaskManagerResponse<UserDto> userResponse = new TaskManagerResponse<>();
		User userEntity = requestTransformer.transformUserEntity(userDto);
		UserDto savedUser = userInformationService.saveOrUpdateUser(userEntity);
		userResponse.setHttpStatus(HttpStatus.OK);
		userResponse.setMessage(ErrorCodes.USER_SAVED.getMessage());
		userResponse.setData(savedUser);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);

	}

	@ApiOperation("Endpoint for saving admin")
	@PostMapping("/admin")
	public ResponseEntity<TaskManagerResponse<?>> saveAdmin(@RequestBody UserDto userDto)
			throws TaskManagerServiceException {
		TaskManagerResponse<UserDto> userResponse = new TaskManagerResponse<>();
		User userEntity = requestTransformer.transformUserEntity(userDto);
		UserDto savedUser = userInformationService.saveOrUpdateUser(userEntity);
		userResponse.setHttpStatus(HttpStatus.OK);
		userResponse.setMessage(ErrorCodes.USER_SAVED.getMessage());
		userResponse.setData(savedUser);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

}
