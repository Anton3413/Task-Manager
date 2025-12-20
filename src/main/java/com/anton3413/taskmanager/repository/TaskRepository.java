package com.anton3413.taskmanager.repository;

import com.anton3413.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByTitleIgnoreCase(String title);

    Optional<Task> findTaskByTitleIgnoreCase(String title);

}

