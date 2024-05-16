package com.hcc.controllers;// HomeController.java
import com.hcc.entities.Authority;
import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.JwtUtil;
import com.hcc.utils.TokenChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenChecker tokenChecker;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            model.addAttribute("loginsuccess", true);

            if (tokenChecker.checkIfRoleReviewer(user.get()))
                model.addAttribute("codereviewer", user.get().getUsername());
            else
                model.addAttribute("username", user.get().getUsername());
        }

        return "index";
    }

//    public boolean checkIfRoleReviewer(User user) {
//        for (GrantedAuthority authority : user.getAuthorities()) {
//            if (authority.getAuthority().equals("ROLE_REVIEWER"))
//                return true;
//        }
//        return false;
//    }
}
