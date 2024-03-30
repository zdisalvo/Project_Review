package com.hcc.controllers;

import com.hcc.entities.User;
import com.hcc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/register")
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/api/register")
    ResponseEntity<?> registerUser() {
        User user = new User();
        user.setUsername("zack");
        user.setPassword("pass");
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }
}
