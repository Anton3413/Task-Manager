package com.anton3413.taskmanager.repository;

import com.anton3413.taskmanager.model.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByTitleIgnoreCaseAndUser_Username(String title, String userUsername);

    Optional<Task> findTaskByTitleIgnoreCaseAndUser_Username(String title, String userUsername);

    List<Task> findAllByUser_Username(String userUsername, Sort sort);

    Optional<Task> findByIdAndUser_Username(Long id, String userUsername);

}

