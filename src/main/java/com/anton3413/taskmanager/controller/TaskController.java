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
import org.springframework.data.domain.Sort;
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
    public String showAllTasks(Model model,
                               @RequestParam(name = "sortField",defaultValue = "id") String sortField,
                               @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {

        Sort sort = getValidatedSort(sortField,sortDir);

        List<TaskSummaryDto> tasks = taskService.findAll(sort)
                .stream()
                .map(taskSummaryDtoMapper::toDto)
                .toList();

        model.addAttribute("tasks",tasks);

        model.addAttribute("sortField", sort.get().findFirst().get().getProperty());
        model.addAttribute("sortDir", sort.get().findFirst().get().getDirection().name().toLowerCase());
        model.addAttribute("reverseSortDir",
                sort.get().findFirst().get().getDirection().isAscending() ? "desc" : "asc");

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
        taskService.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/updateStatus")
    public String editTaskStatus(@RequestParam("taskId") Long id,
                                 @RequestParam("status") String status){

        taskService.updateStatus(id,status);

        return "redirect:/tasks";
    }

    private Sort getValidatedSort(String fieldName, String direction) {

        final List<String> allowedFields = List.of("id","title","dueDate", "status");

        String safeField = allowedFields.contains(fieldName) ? fieldName : "id";
        Sort.Direction safeDir = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return Sort.by(safeDir, safeField);
    }
}
