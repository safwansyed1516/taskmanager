package com.app.security.config;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.app.dto.UserDto;
import com.app.entity.AuthToken;
import com.app.entity.User;
import com.app.exception.TaskManagerServiceException;
import com.app.utils.CommonUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CommonUtils commonUtils;

	public String getUsernameFromToken(String token) {
		return this.getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return this.getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = this.getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(commonUtils.signingKey).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		if (expiration == null) {
			return Boolean.FALSE;
		}
		return expiration.before(new Date());
	}

	public AuthToken generateToken(User user) {
		return this.doGenerateToken(user.getEmail(), user.getUserId(), user.getRoleId());
	}

	private AuthToken doGenerateToken(String subject, Long userId, Long roleId) {

		final Claims claims = Jwts.claims().setSubject(subject);
		claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

		final AuthToken authToken = new AuthToken();
		authToken.setAccessToken(Jwts.builder().setClaims(claims).setIssuer("http://devglan.com")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (commonUtils.accessTokenValiditySeconds * 1000)))
				.signWith(SignatureAlgorithm.HS256, commonUtils.signingKey).compact());
		authToken.setAccessTokenValidationTime((commonUtils.accessTokenValiditySeconds * 1000));
		authToken.setRefreshToken(Jwts.builder().setClaims(claims).setIssuer("http://devglan.com")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (commonUtils.accessTokenValiditySeconds * 1000)))
				.signWith(SignatureAlgorithm.HS256, commonUtils.signingKey).compact());

		return authToken;

	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = this.getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !this.isTokenExpired(token));
	}

	public AuthToken parseToken(UserDto userInformationResponse) throws TaskManagerServiceException {

		return this.doGenerateToken(userInformationResponse.getEmail(), userInformationResponse.getUserId(),
				userInformationResponse.getRoleId());

	}

	public void setLogedinUser(String key, UserDto obj, HttpServletRequest req) {

		req.setAttribute(key, obj);
	}

	public UserDto getLoggedinUser(String key, HttpServletRequest req) {
		return (UserDto) req.getAttribute(key);

	}

}
