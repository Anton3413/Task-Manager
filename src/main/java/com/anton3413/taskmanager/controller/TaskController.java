package com.anton3413.taskmanager.controller;


import com.anton3413.taskmanager.dto.CreateTaskDto;
import com.anton3413.taskmanager.dto.EditTaskDto;
import com.anton3413.taskmanager.dto.ResponseTaskDto;
import com.anton3413.taskmanager.dto.TaskSummaryDto;
import com.anton3413.taskmanager.mapper.CreateTaskDtoMapper;
import com.anton3413.taskmanager.mapper.EditTaskDtoMapper;
import com.anton3413.taskmanager.mapper.ResponseTaskDtoMapper;
import com.anton3413.taskmanager.mapper.TaskSummaryDtoMapper;
import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.model.Task;
import com.anton3413.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskSummaryDtoMapper taskSummaryDtoMapper;
    private final CreateTaskDtoMapper createTaskDtoMapper;
    private final ResponseTaskDtoMapper responseTaskDtoMapper;
    private final EditTaskDtoMapper editTaskDtoMapper;

    @ModelAttribute("statuses")
    public Status[] addStatuses(){
        return Status.values();
    }

    @GetMapping
    public String showAllTasks(Model model){
        List<TaskSummaryDto> tasks = taskService.findAll()
                .stream()
                .map(taskSummaryDtoMapper::toDto)
                .toList();
        model.addAttribute("tasks",tasks);
        return "tasks";
    }

    @GetMapping("/{id}")
    public String showTaskDetails(@PathVariable Long id, Model model){

       ResponseTaskDto taskDto =  responseTaskDtoMapper.toDto(taskService.findById(id));

       model.addAttribute("task",taskDto);

       return "task-details";
    }

    @GetMapping("/new")
    public String showCreateTaskPage(Model model){
        model.addAttribute("task", CreateTaskDto.builder().build());
        return "create-task";
    }

    @PostMapping("/new")
    public String createNewTask(@Valid @ModelAttribute("task") CreateTaskDto createTaskDto,
                                BindingResult result){

        if(result.hasErrors()){
            return "create-task";
        }

        taskService.save(createTaskDtoMapper.toEntity(createTaskDto));
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTaskById(@PathVariable Long id){
        taskService.deleteById(id);

        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskPage(@PathVariable Long id, Model model){

        EditTaskDto task = editTaskDtoMapper.toDto(taskService.findById(id));

        model.addAttribute("task",task);

        return "edit-task";
    }

    @PostMapping("/edit")
    public String editTask( @Valid @ModelAttribute("task") EditTaskDto editTaskDto,
                            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "edit-task";
        }
        Task task = editTaskDtoMapper.toEntity(editTaskDto);
        System.out.println(task.getId());
        System.out.println(editTaskDto.getId());
        taskService.save(task);
        return "redirect:/tasks";
    }

}
