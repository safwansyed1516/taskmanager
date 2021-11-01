package com.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {

	private String accessToken;

	private String refreshToken;

	private long accessTokenValidationTime;

}
