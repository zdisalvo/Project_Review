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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserDashboardController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @RequestMapping(value = "/api/assignments", method = RequestMethod.GET)
    public String showDashboard(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        //String username = jwtUtil.getUsernameFromToken(jwtUtil.getToken());

        //find Rework Assignments
        List<Assignment> reworkAssignments = assignmentRepository.findAssignmentsByUserAndStatus(user, "REWORK").orElse(null);
        if (!reworkAssignments.isEmpty())
            model.addAttribute("rework", reworkAssignments);

        //find Completed Assignments
        List<Assignment> completedAssignments = assignmentRepository.findAssignmentsByUserAndStatus(user, "COMPLETED").orElse(null);
        if (!completedAssignments.isEmpty())
            model.addAttribute("completed", completedAssignments);

        //find In Review Assignments
        List<Assignment> reviewAssignments = assignmentRepository.findAssignmentsByUserAndStatus(user, "REVIEW").orElse(null);
        if(!reworkAssignments.isEmpty())
            model.addAttribute("review", reviewAssignments);

        return "mydashboard";
    }

//    @RequestMapping("/api/assignments/{id}")
//    public String getAssignmentById(Model model, @PathVariable("id") String idString) {
//        // Your logic to fetch assignment by ID
//        Long id = Long.parseLong(idString);
//        Optional<Assignment> assignmentOptional = assignmentRepository.findAssignmentById(id);
//
//        if(assignmentOptional.isEmpty()) {
//            model.addAttribute("noAssignment", "No Assignment exists with id " + idString);
//            return "assignmentDisplay";
//        }
//
//        Assignment assignment = assignmentOptional.get();
//
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Optional<User> user = userRepository.findByUsername(username);
//
//        if(assignment.getUser().equals(user.get()))
//            return "assignmentEdit.html";
//        else
//            return "assignmentDisplay";
//
//    }

    @RequestMapping(value = "/api/assignments", method = RequestMethod.POST)
    public String submitAssignment(Model model, @RequestParam String username, @RequestParam String branch,
                                   @RequestParam String githubUrl, @RequestParam String reviewVideoUrl) {

        User user = userRepository.findByUsername(username).orElseThrow();

        Assignment assignment = new Assignment("REVIEW", branch, reviewVideoUrl, githubUrl, user);
        assignmentRepository.save(assignment);

        return showDashboard(model);
    }

}
