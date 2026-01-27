package com.anton3413.taskmanager.controller;


import com.anton3413.taskmanager.dto.user.CreateUserDto;
import com.anton3413.taskmanager.mapper.UserMapper;
import com.anton3413.taskmanager.model.User;
import com.anton3413.taskmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping("/login")
    String displayLoginPage(Authentication authentication){
        if(authentication != null){
            return "redirect:/logout";
        }
        return "login";
    }

    @GetMapping("/registration")
    String displayRegistrationPage(Model model, Authentication authentication){

        if(authentication != null){
            return "redirect:/logout";
        }
        model.addAttribute("userDto", CreateUserDto.builder().build());
        return "registration";
    }

    @PostMapping("/registration")
    String createNewUser(@Valid @ModelAttribute("userDto") CreateUserDto userDto, BindingResult result,
                         RedirectAttributes attributes){
        if(result.hasErrors()){
            return "/registration";
        }

        User newUser = userMapper.fromCreateUserDtoToEntity(userDto);
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userService.save(newUser);

        attributes.addFlashAttribute("message", "Registration was successful!");
        attributes.addFlashAttribute("messageType", "success");

        return "redirect:/login";
    }

    @GetMapping("/logout")
    String displayLogoutPage(Authentication authentication){
        if (authentication == null) {
            return "redirect:/login";
        } else return "logout";
    }
}
