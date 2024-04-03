package com.hcc.controllers;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.exceptions.ResourceNotFoundException;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserDashboardController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @RequestMapping(value = "/mydashboard", method = RequestMethod.GET)
    public String showDashboard(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        //String username = jwtUtil.getUsernameFromToken(jwtUtil.getToken());

        //find Rework Assignments
        List<Assignment> reworkAssignments = assignmentRepository.findAssignmentsByUserAndStatus(username, "REWORK").orElse(null);
        model.addAttribute("rework", reworkAssignments);

        //find Completed Assignments
        List<Assignment> completedAssignments = assignmentRepository.findAssignmentsByUserAndStatus(username, "COMPLETED").orElse(null);
        model.addAttribute("completed", completedAssignments);

        //find In Review Assignments
        List<Assignment> reviewAssignments = assignmentRepository.findAssignmentsByUserAndStatus(username, "REVIEW").orElse(null);
        model.addAttribute("review", reviewAssignments);

        return "mydashboard";
    }

    @RequestMapping(value = "/mydashboard", method = RequestMethod.POST)
    public String submitAssignment(Model model, @RequestParam String username, @RequestParam String branch,
                            @RequestParam String githubUrl, @RequestParam String reviewVideoUrl) {

        User user = userRepository.findByUsername(username).orElseThrow();

        Assignment assignment = new Assignment("REVIEW", branch, reviewVideoUrl, githubUrl, user);
        assignmentRepository.save(assignment);

        return "mydashboard";
    }

}
