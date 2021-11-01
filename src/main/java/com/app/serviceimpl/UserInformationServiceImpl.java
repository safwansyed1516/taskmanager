package com.app.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.dto.UserDto;
import com.app.entity.User;
import com.app.exception.ErrorCodes;
import com.app.exception.TaskManagerServiceException;
import com.app.repository.LoginRepository;
import com.app.service.UserInformationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserInformationServiceImpl implements UserInformationService {

	@Autowired
	LoginRepository loginRepository;

	public UserDto getUserByMailId(String email) throws TaskManagerServiceException {
		try {
			UserDto userDto = new UserDto();
			final User userByEmail = loginRepository.findByEmailAndIsDeleted(email, Boolean.FALSE);
			BeanUtils.copyProperties(userByEmail, userDto);
			return userDto;

		} catch (final Exception ex) {
			log.error("Exception at getting user : ", ex);
			throw ErrorCodes.FAILED_TO_GET_USER.createTaskManagerServiceException(email);
		}
	}

	@Override
	public UserDto saveOrUpdateUser(User userEntity, boolean isAdd) throws TaskManagerServiceException {
		try {
			User savedUserEntity = loginRepository.save(userEntity);
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(savedUserEntity, userDto);
			return userDto;

		} catch (Exception ex) {
			log.error("Excepton at save/update user", ex);
			throw ErrorCodes.FAILED_TO_SAVE_USER.createTaskManagerServiceException(userEntity.getEmail());
		}

	}

	@Override
	public String deleteUser(Long userId) throws TaskManagerServiceException {
		try {
			int delete = loginRepository.deleteUser(userId);
			if (delete > 0) {
				return ErrorCodes.USER_DELETED.getMessage();
			} else {
				return ErrorCodes.FAILED_TO_DELETE_USER.getMessage();
			}
		} catch (Exception ex) {
			log.error("Excepton at delete user", ex);
			throw ErrorCodes.FAILED_TO_DELETE_USER.createTaskManagerServiceException();
		}

	}

	@Override
	public List<UserDto> getAllUsers() throws TaskManagerServiceException {
		try {
			List<User> users = loginRepository.findAll();
			List<UserDto> userDto = new ArrayList<>();
			BeanUtils.copyProperties(users, userDto);
			return userDto;
		} catch (Exception ex) {
			log.error("Exception at Get All User", ex);
		}
		return new ArrayList<>();
	}

}
