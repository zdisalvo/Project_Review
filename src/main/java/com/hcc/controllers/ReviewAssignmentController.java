package com.hcc.controllers;


import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ReviewAssignmentController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    ReviewDashboardController reviewDashboardController;

    @RequestMapping(value = "/rejectproject", method = RequestMethod.POST)
    public String rejectProject(Model model, @RequestParam("projectId") Long projectId,
                                @RequestParam("codeReviewer") String codeReviewer) {

        Optional<Assignment> assignmentOptional = assignmentRepository.findAssignmentById(projectId);
        Assignment assignment = new Assignment();

        Optional<User> userOptional = userRepository.findByUsername(codeReviewer);

        if (assignmentOptional.isPresent() && userOptional.isPresent()) {
            assignment = assignmentOptional.get();
            assignment.setStatus("NEEDS_UPDATE");
            assignment.setCodeReviewer(userOptional.get());
            assignmentRepository.save(assignment);
        }

        return reviewDashboardController.showDashboard(model);
    }

    @RequestMapping(value = "/completedproject", method = RequestMethod.POST)
    public String completedProject(Model model, @RequestParam("projectId") Long projectId,
                                @RequestParam("codeReviewer") String codeReviewer) {

        Optional<Assignment> assignmentOptional = assignmentRepository.findAssignmentById(projectId);
        Assignment assignment = new Assignment();

        Optional<User> userOptional = userRepository.findByUsername(codeReviewer);

        if (assignmentOptional.isPresent() && userOptional.isPresent()) {
            assignment = assignmentOptional.get();
            assignment.setStatus("COMPLETED");
            assignment.setCodeReviewer(userOptional.get());
            assignmentRepository.save(assignment);
        }

        return reviewDashboardController.showDashboard(model);
    }
}
