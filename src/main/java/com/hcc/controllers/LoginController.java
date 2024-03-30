package com.hcc.controllers;

import com.hcc.entities.LoginRequest;
import com.hcc.entities.User;
import com.hcc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

//@Controller
//public class LoginController {
//
//    @GetMapping("/login")
//    public String home() {
//        return "login";
//    }
//}

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    Logger logger;

    @RequestMapping(value = "/login", method=RequestMethod.GET)
    public String showLoginPage(ModelMap map) {
        // Return the path to the login page
        return "login"; // Assuming "login" is the name of your login page HTML file
    }

    //@RequestMapping(value="/api/auth/login", method = RequestMethod.POST)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(@RequestParam String username, @RequestParam String password) {
//        String username = loginRequest.getUsername();
//        String password = loginRequest.getPassword();

        // Validate user credentials
        boolean isValidUser = userService.validateUser(username, password);

        //logger.info(username);

        //if (username.equals("admin") && password.equals("pass"))
        if (isValidUser) {
            // Return success response if user is valid
//            model.put("name", username);
//            model.put("password", password);

            //model.addAttribute("error", true);
            return "index";
            //return ResponseEntity.ok().build();
        } else {
            // Return error response if user is not valid
//            model.put("errorMessage", "Invalid Credentials");
            return "login";
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
