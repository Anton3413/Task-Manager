package com.anton3413.taskmanager.service;

import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.model.User;
import com.anton3413.taskmanager.repository.TaskRepository;
import com.anton3413.taskmanager.service.impl.TaskServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    private static final String USERNAME = "John_doe";
    private static final Long TASK_ID = 1L;
    private static final Long USERNAME_ID = 1L;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    void findById_ShouldReturnTask_WhenTaskExistsAndBelongsToUser() {

        Task expectedTask = Task.builder().id(TASK_ID).build();

        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.findByIdAndUser_Username(TASK_ID, USERNAME)).thenReturn(Optional.of(expectedTask));

        Task actualTask = taskService.findById(TASK_ID);

        assertNotNull(actualTask);
        assertEquals(TASK_ID, actualTask.getId());
        verify(securityService).getCurrentUsername();
        verify(taskRepository).findByIdAndUser_Username(TASK_ID, USERNAME);
    }

    @Test
    void findById_shouldThrowExceptionWhenTaskNotFoundOrAccessDenied() {
        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.findByIdAndUser_Username(TASK_ID, USERNAME)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.findById(TASK_ID));
        verify(securityService).getCurrentUsername();
        verify(taskRepository).findByIdAndUser_Username(TASK_ID, USERNAME);
    }

    @Test
    void deleteById_shouldDeleteTaskWhenExistsAndBelongsToUser() {
        Task expectedTask = Task.builder().id(TASK_ID).build();

        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.findByIdAndUser_Username(TASK_ID, USERNAME)).thenReturn(Optional.of(expectedTask));

        assertDoesNotThrow(() -> taskService.deleteById(TASK_ID));
        verify(securityService).getCurrentUsername();
        verify(taskRepository).findByIdAndUser_Username(TASK_ID, USERNAME);
        verify(taskRepository).delete(expectedTask);
    }

    @Test
    void deleteById_shouldThrowExceptionWhenTaskNotFoundOrAccessDenied() {
        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.findByIdAndUser_Username(TASK_ID, USERNAME)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.deleteById(TASK_ID));
        verify(securityService).getCurrentUsername();
        verify(taskRepository).findByIdAndUser_Username(TASK_ID, USERNAME);
    }

    @Test
    void save_shouldSuccessfullySaveNewTask() {
        User user = User.builder().id(USERNAME_ID)
                .username(USERNAME).build();

        Task task = Task.builder().build();

        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(userService.findByUsername(USERNAME)).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        assertDoesNotThrow(() -> taskService.save(task));

        assertNotNull(task.getUser());
        assertEquals(USERNAME, task.getUser().getUsername());

        verify(securityService).getCurrentUsername();
        verify(userService).findByUsername(USERNAME);
        verify(taskRepository).save(task);
    }

    @Test
    void findAll_shouldReturnTasksForCurrentUserWithSorting() {

        List<Task> expectedList = List.of(
                Task.builder().id(1L).title("Task 1").build(),
                Task.builder().id(2L).title("Task 2").build(),
                Task.builder().id(3L).title("Task 3").build()
        );

        Sort sort = Sort.by("title").ascending();


        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.findAllByUser_Username(USERNAME, sort)).thenReturn(expectedList);

        List<Task> resultList = taskService.findAll(sort);

        assertNotNull(resultList);
        assertEquals(expectedList, resultList);

        verify(securityService).getCurrentUsername();
        verify(taskRepository).findAllByUser_Username(USERNAME, sort);
    }

    @Test
    void findAll_shouldReturnEmptyListWhenUserHasNoTasks() {

        Sort sort = Sort.by("id");

        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.findAllByUser_Username(USERNAME, sort)).thenReturn(List.of());

        List<Task> resultList = taskService.findAll(sort);

        assertNotNull(resultList);
        assertTrue(resultList.isEmpty());

        verify(securityService).getCurrentUsername();
        verify(taskRepository).findAllByUser_Username(USERNAME, sort);
    }

    @Test
    void existsByTitleIgnoreCase_shouldReturnTrueWhenTaskExistsAndUserHasIt() {

        final String taskName = "Example task";

        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.existsByTitleIgnoreCaseAndUser_Username(taskName, USERNAME)).thenReturn(true);

        assertTrue(taskService.existsByTitleIgnoreCase(taskName));
        verify(securityService).getCurrentUsername();
        verify(taskRepository).existsByTitleIgnoreCaseAndUser_Username(taskName, USERNAME);
    }

    @Test
    void existsByTitleIgnoreCase_shouldReturnFalseWhenTaskDoesntExistOrUserDoesntHave() {

        final String taskName = "Example task";

        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.existsByTitleIgnoreCaseAndUser_Username(taskName, USERNAME)).thenReturn(false);

        assertFalse(taskService.existsByTitleIgnoreCase(taskName));
        verify(securityService).getCurrentUsername();
        verify(taskRepository).existsByTitleIgnoreCaseAndUser_Username(taskName, USERNAME);
    }

    @Test
    void findTaskByTitleIgnoreCase_shouldReturnNotEmptyOptionalWhenTaskExists() {

        final String taskName = "Example task";
        Task expectedTask = Task.builder().id(TASK_ID).build();

        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.findTaskByTitleIgnoreCaseAndUser_Username(taskName, USERNAME))
                .thenReturn(Optional.of(expectedTask));

      Optional<Task> result = (taskService.findTaskByTitleIgnoreCase(taskName));
      assertTrue(result.isPresent());
      assertEquals(expectedTask.getId(), result.get().getId());

      verify(securityService).getCurrentUsername();
      verify(taskRepository).findTaskByTitleIgnoreCaseAndUser_Username(taskName, USERNAME);
    }

    @Test
    void findTaskByTitleIgnoreCase_shouldReturnNullableWhenTaskDoesntExist() {

        final String taskName = "Example task";

        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.findTaskByTitleIgnoreCaseAndUser_Username(taskName, USERNAME))
                .thenReturn(Optional.empty());

        Optional<Task> result = (taskService.findTaskByTitleIgnoreCase(taskName));
        assertFalse(result.isPresent());

        verify(securityService).getCurrentUsername();
        verify(taskRepository).findTaskByTitleIgnoreCaseAndUser_Username(taskName, USERNAME);
    }

    @Test
    void updateStatus_shouldSuccessfullyUpdateTaskStatus(){

       final Task task = Task.builder().id(TASK_ID).build();
        String status = Status.IN_PROGRESS.name();
        when(securityService.getCurrentUsername()).thenReturn(USERNAME);
        when(taskRepository.findByIdAndUser_Username(TASK_ID, USERNAME))
                .thenReturn(Optional.of(task));

        taskService.updateStatus(TASK_ID, status);

        assertNotNull(task.getStatus());
        assertEquals(Status.valueOf(status), task.getStatus());

        verify(securityService).getCurrentUsername();
        verify(taskRepository).findByIdAndUser_Username(TASK_ID, USERNAME);

    }
}
