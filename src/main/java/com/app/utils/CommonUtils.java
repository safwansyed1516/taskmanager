package com.app.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

	@Value("${application.tokenValidity}")
	public long accessTokenValiditySeconds;

	@Value("${application.signingKey}")
	public String signingKey;

	@Value("${application.adminRoleName}")
	public String adminRoleName;

	@Value("${application.tokenPrefix}")
	public String tokenPrefix;

	@Value("${application.headerString}")
	public String headerString;
}
