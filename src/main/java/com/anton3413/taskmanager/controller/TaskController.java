package com.anton3413.taskmanager.controller;


import com.anton3413.taskmanager.dto.CreateTaskDto;
import com.anton3413.taskmanager.dto.ResponseTaskDto;
import com.anton3413.taskmanager.model.Status;
import com.anton3413.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @ModelAttribute("statuses")
    public Status[] addStatuses(){
        return Status.values();
    }

    @GetMapping
    public String showAllTasks(Model model){
        model.addAttribute("tasks",taskService.findAll());
        return "tasks";
    }

    @GetMapping("/{id}")
    public String showTaskDetails(@PathVariable Long id, Model model){

       ResponseTaskDto taskDto =  taskService.findById(id);

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

        taskService.save(createTaskDto);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTaskById(@PathVariable Long id){
        taskService.deleteById(id);

        return "redirect:/tasks";
    }

}
