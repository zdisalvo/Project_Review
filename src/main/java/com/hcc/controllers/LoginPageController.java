//package com.hcc.controllers;
//
//import com.hcc.entities.User;
//import com.hcc.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class LoginPageController {
//
//    @Autowired
//    UserService userService;
//
//    @GetMapping
//    public String showLoginPage() {
//        // Return the path to the login page
//        return "login"; // Assuming "login" is the name of your login page HTML file
//    }
//
//    @RequestMapping(value="/login", method = RequestMethod.POST)
//    ResponseEntity<?> loginUser(@RequestAttribute("username") String username, @RequestAttribute("password") String password) {
////        String username = loginRequest.getUsername();
////        String password = loginRequest.getPassword();
//
//        // Validate user credentials
//        boolean isValidUser = userService.validateUser(username, password);
//        if (isValidUser) {
//            // Return success response if user is valid
//            return ResponseEntity.ok().build();
//        } else {
//            // Return error response if user is not valid
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
//}