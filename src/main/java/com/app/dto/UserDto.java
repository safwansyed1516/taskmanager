package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long userId;

	private String email;

	private String firstName;

	private String lastName;

	private String mobileNo;

	private String password;

	private Long roleId;

	public Boolean isDeleted;
}
