package com.anton3413.taskmanager.config.exception.handler;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundExc(EntityNotFoundException exception, Model model){
        model.addAttribute("statusCode",HttpStatus.NOT_FOUND.value());
        model.addAttribute("errorTitle",HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("errorMessage", exception.getMessage());

        return "errors/common-error";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleArgumentParsingExc(MethodArgumentTypeMismatchException exception, Model model){

        final String PARAMETER_PARSING_EXCEPTION = "The provided ID must be a number. " +
                "Please check the URL and try again.";

        model.addAttribute("statusCode",HttpStatus.BAD_REQUEST.value());
        model.addAttribute("errorTitle",HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("errorMessage", PARAMETER_PARSING_EXCEPTION);

        return  "errors/common-error";
    }
}
