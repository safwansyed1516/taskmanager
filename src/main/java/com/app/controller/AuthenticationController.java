package com.app.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserDto;
import com.app.entity.AuthToken;
import com.app.entity.User;
import com.app.exception.ErrorCodes;
import com.app.exception.TaskManagerServiceException;
import com.app.repository.LoginRepository;
import com.app.security.config.JwtTokenUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private LoginRepository loginRepository;

	/**
	 * Endpoint for login and generating of token.
	 *
	 * @param loginUser
	 * @return
	 * @throws AuthenticationException
	 * @throws {@link                  TaskManagerServiceException}
	 */
	@ApiOperation(value = "Endpoint for generating token for login")
	@PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
	public ResponseEntity<AuthToken> register(@RequestBody UserDto loginUser) throws TaskManagerServiceException {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			User byEmail = loginRepository.getByEmail(loginUser.getEmail());
			if (byEmail == null) {
				throw ErrorCodes.UNAUTHORIZED.createTaskManagerServiceException(loginUser.getEmail());
			} else {
				return ResponseEntity.ok(jwtTokenUtil.generateToken(byEmail));
			}

		} catch (Exception ex) {
			throw ErrorCodes.UNAUTHORIZED.createTaskManagerServiceException(loginUser.getEmail());
		}
	}

}
