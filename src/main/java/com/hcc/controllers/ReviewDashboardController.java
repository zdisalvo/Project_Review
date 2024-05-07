package com.hcc.controllers;


import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ReviewDashboardController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @RequestMapping(value = "/api/assignments/review", method = RequestMethod.GET)
    public String showDashboard(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent())
            model.addAttribute("username", user.get().getUsername());

        //String username = jwtUtil.getUsernameFromToken(jwtUtil.getToken());


        //find In Review Assignments
//        List<String> status = new ArrayList<>();
//        status.add("IN_REVIEW");
//        status.add("RESUBMITTED");
        List<Assignment> reviewAssignments = assignmentRepository.findAssignmentsByStatus("IN_REVIEW").orElse(null);
        if (!reviewAssignments.isEmpty())
            model.addAttribute("review", reviewAssignments);

        return "reviewerdashboard";
    }
}
