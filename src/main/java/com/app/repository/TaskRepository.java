package com.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByPagination(PageRequest pageRequest);

	@Modifying
	@Transactional
	@Query("UPDATE Task t SET t.isDeleted=true")
	int deleteTask(Long taskId);

}
