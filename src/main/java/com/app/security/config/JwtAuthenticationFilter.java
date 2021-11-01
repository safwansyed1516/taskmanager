package com.app.security.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.dto.UserDto;
import com.app.exception.ErrorCodes;
import com.app.exception.TaskManagerServiceException;
import com.app.service.UserInformationService;
import com.app.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserInformationService userInformationService;

	@Autowired
	private CommonUtils commonUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		final String header = req.getHeader(commonUtils.headerString);
		String username = null;
		String authToken = null;
		if ((header != null) && header.startsWith(commonUtils.tokenPrefix)) {
			authToken = header.replace(commonUtils.tokenPrefix, "");
			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (final IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
				try {
					throw ErrorCodes.UNAUTHORIZED.createTaskManagerServiceException(e);
				} catch (TaskManagerServiceException e1) {
					e1.printStackTrace();
				}

			} catch (final ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
				try {
					throw ErrorCodes.TOKEN_EXPIRED.createTaskManagerServiceException(e);
				} catch (TaskManagerServiceException e1) {
					e1.printStackTrace();
				}

			} catch (final SignatureException e) {
				logger.error("Authentication Failed. Username or Password not valid.");
				try {
					throw ErrorCodes.INVALID_TOKEN.createTaskManagerServiceException(e);
				} catch (TaskManagerServiceException e1) {
					e1.printStackTrace();
				}

			} catch (final Exception e) {
				logger.error("Authentication Failed. Username or Password not valid.");
				try {
					throw ErrorCodes.INVALID_TOKEN.createTaskManagerServiceException(e);
				} catch (TaskManagerServiceException e1) {
					e1.printStackTrace();
				}

			}
		} else {
			logger.warn("couldn't find bearer string, will ignore the header");
		}
		if ((username != null) && (SecurityContextHolder.getContext().getAuthentication() == null)) {

			final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UserDto userByMailId = null;
			if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(authToken, userDetails))) {
				final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				logger.info("authenticated user " + username + ", setting security context");
				try {
					userByMailId = userInformationService.getUserByMailId(username);
					jwtTokenUtil.setLogedinUser("user", userByMailId, req);

				} catch (final TaskManagerServiceException e) {
					try {
						throw ErrorCodes.UNAUTHORIZED.createTaskManagerServiceException(e.getMessage());
					} catch (TaskManagerServiceException e1) {
						log.error(e1.toString());
					}

				}

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(req, res);
	}

	public String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
}
