package com.app.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskManagerResponse<T> {

	HttpStatus httpStatus;
	String message;
	T data;
}
