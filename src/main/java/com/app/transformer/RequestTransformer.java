package com.app.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.dto.UserDto;
import com.app.entity.User;
import com.app.service.UserInformationService;

@Component
public class RequestTransformer {

	@Autowired
	UserInformationService userInformationService;

	public User transformUserEntity(UserDto userDto) {
		User user = null;
		if (userDto.getUserId() == null) {
			user = new User();
		} else {
			userInformationService.getUserByUserId(userDto.getUserId());
		}
		BeanUtils.copyProperties(userDto, user);
		user.setIsDeleted(Boolean.FALSE);
		return user;
	}

}
