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
@Table(name = "task")
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3124525926748615884L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id", unique = true, nullable = false)
	private Long taskId;

	@Column(name = "task_name", columnDefinition = "Varchar(255) default ' '")
	private String taskName;

	@Column(name = "is_assigned", columnDefinition = "TinyInt(1) default ' '")
	private Boolean isAssigned;

	@Column(name = "assigned_to", columnDefinition = "Varchar(255) default ' '")
	private String assignedTo;

	@Column(name = "is_complete", columnDefinition = "TinyInt(1) default ' '")
	private Boolean isComplete;

	@Column(name = "is_deleted", columnDefinition = "TinyInt(1) default ' '")
	private Boolean isDeleted;

	@Column(name = "category_id")
	private Long categoryId;

}
