package com.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.dto.UserDto;
import com.app.entity.User;
import com.app.exception.TaskManagerServiceException;

@Service
public interface UserInformationService {

	public UserDto getUserByMailId(String email) throws TaskManagerServiceException;

	public UserDto saveOrUpdateUser(User userEntity, boolean isAdd) throws TaskManagerServiceException;

	public String deleteUser(Long userId) throws TaskManagerServiceException;

	public List<UserDto> getAllUsers() throws TaskManagerServiceException;

}
