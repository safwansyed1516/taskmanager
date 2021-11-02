package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

	private Long taskId;

	private String taskName;

	private Boolean isAssigned;

	private String assignedTo;

	private Boolean isComplete;

	private Long categoryId;

}
