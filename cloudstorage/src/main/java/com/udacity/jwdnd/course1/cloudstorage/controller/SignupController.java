package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getSignupPage(){
        return "signup";
    }

    @PostMapping()
    public String createUser(User user, Model model){
        String signupError = null;
        if(!userService.isUsernameAvailible(user.getUsername())){
            signupError = "Username already exists.";
        }

        if(signupError == null){
            if(userService.createUser(user) < 0){
                signupError = "Error";
            }
        }

        if(signupError == null){
            model.addAttribute("signupSuccess", "You successfully signed up!");
        }else{
            model.addAttribute("signupError",signupError);
        }

        return "signup";
    }
}
