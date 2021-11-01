package com.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1873973040952797159L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private Long userId;

	@Column(name = "email", columnDefinition = "Varchar(255) default ' '")
	private String email;

	@Column(name = "first_name", columnDefinition = "Varchar(255) default ' '")
	private String firstName;

	@Column(name = "last_name", columnDefinition = "Varchar(255) default ' '")
	private String lastName;

	@Column(name = "mobile_no", columnDefinition = "Varchar(255) default ' '")
	private String mobileNo;

	@Column(name = "password", columnDefinition = "Varchar(255) default ' '")
	private String password;

	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "is_deleted")
	public Boolean isDeleted;

}
