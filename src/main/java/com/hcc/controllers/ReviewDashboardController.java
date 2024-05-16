package com.hcc.controllers;


import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.JwtUtil;
import com.hcc.utils.TokenChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    TokenChecker tokenChecker;

    @RequestMapping(value = "/api/assignments/review", method = RequestMethod.GET)
    public String showDashboard(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

//        if (user.isPresent())
//            model.addAttribute("username", user.get().getUsername());

        if (user.isPresent()) {

            if (tokenChecker.checkIfRoleReviewer(user.get()))
                model.addAttribute("codereviewer", user.get().getUsername());
            else
                model.addAttribute("username", user.get().getUsername());
        }

        //String username = jwtUtil.getUsernameFromToken(jwtUtil.getToken());


        //find In Review Assignments
//        List<String> status = new ArrayList<>();
//        status.add("IN_REVIEW");
//        status.add("RESUBMITTED");
        List<Assignment> reviewAssignments = assignmentRepository.findAssignmentsByStatus("IN_REVIEW").orElse(null);
        List<Assignment> resubmittedAssignments = assignmentRepository.findAssignmentsByStatus("RESUBMITTED").orElse(null);
        reviewAssignments.addAll(resubmittedAssignments);
        if (!reviewAssignments.isEmpty())
            model.addAttribute("review", reviewAssignments);

        return "reviewerdashboard";
    }

    @RequestMapping(value = "api/assignments/review/{id}", method = RequestMethod.GET)
    public String reviewAssignmentById(Model model, @PathVariable("id") String idString) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        Long id = Long.parseLong(idString);
        Optional<Assignment> assignmentOptional = assignmentRepository.findAssignmentById(id);
        Assignment assignment = new Assignment();
        if (assignmentOptional.isPresent()) {
            assignment = assignmentOptional.get();
            model.addAttribute("assignment", assignment);
        }

        if (user.isPresent()) {
            //model.addAttribute("username", user.get().getUsername());
            model.addAttribute("codeReviewer", user.get().getUsername());

//            if (assignment.getCodeReviewer() != null)
//                model.addAttribute("codeReviewer");
//            else if (user.get().getAuthorities().contains("ROLE_REVIEWER")) {
//                assignment.setCodeReviewer(user.get());
//                assignmentRepository.save(assignment);
//                model.addAttribute("codeReviewer");
//            }

        }

        return "assignmentReview";
    }
}
