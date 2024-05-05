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
public class EditAssignmentController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    UserDashboardController userDashboardController;

        @RequestMapping(value = "/api/editassignment", method = RequestMethod.GET)
    public String showAssignments(Model model) {
        return userDashboardController.showDashboard(model);
    }

    @RequestMapping(value = "/api/editassignment", method = RequestMethod.POST)
    public String editAssignment(Model model, @RequestParam String id, @RequestParam String status,
                                 @RequestParam String branch, @RequestParam String githubUrl,
                                 @RequestParam String reviewVideoUrl, @RequestParam String username) {

        User user = userRepository.findByUsername(username).orElseThrow();

        long idL = Long.parseLong(id);

        Optional<Assignment> assignmentOptional = assignmentRepository.findAssignmentById(idL);



        if(assignmentOptional.isPresent()) {

            Assignment assignment = assignmentOptional.get();
            assignment.setGithubUrl(githubUrl);
            assignment.setReviewVideoUrl(reviewVideoUrl);

//            Assignment assignment2 = new Assignment("REVIEW", branch, reviewVideoUrl, githubUrl, user);
//            assignmentRepository.save(assignment2);

            assignmentRepository.save(assignment);
        }
        //throw new ResourceNotFoundException("test");

        return userDashboardController.showDashboard(model);
    }
}
