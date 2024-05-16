package com.hcc.controllers;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;
import com.hcc.exceptions.ResourceNotFoundException;
import com.hcc.repositories.AssignmentRepository;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.JwtUtil;
import com.hcc.utils.TokenChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    TokenChecker tokenChecker;

    @RequestMapping(value = "/api/assignments", method = RequestMethod.GET)
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

        //find Rework Assignments
        List<Assignment> reworkAssignments = assignmentRepository.findAssignmentsByUserAndStatus(user, "NEEDS_UPDATE").orElse(null);
        if (!reworkAssignments.isEmpty())
            model.addAttribute("rework", reworkAssignments);

        //find Completed Assignments
        List<Assignment> completedAssignments = assignmentRepository.findAssignmentsByUserAndStatus(user, "COMPLETED").orElse(null);
        if (!completedAssignments.isEmpty())
            model.addAttribute("completed", completedAssignments);

        //find In Review Assignments
        List<Assignment> reviewAssignments = assignmentRepository.findAssignmentsByUserAndStatus(user, "IN_REVIEW").orElse(null);
        List<Assignment> resubmittedAssignments = assignmentRepository.findAssignmentsByUserAndStatus(user, "RESUBMITTED").orElse(null);
        reviewAssignments.addAll(resubmittedAssignments);
        if (!reviewAssignments.isEmpty())
            model.addAttribute("review", reviewAssignments);

        return "mydashboard";
    }

        @RequestMapping("/api/assignments/{id}")
    public String getAssignmentById(Model model, @PathVariable("id") String idString) {


        Long id = Long.parseLong(idString);
        Optional<Assignment> assignmentOptional = assignmentRepository.findAssignmentById(id);

        String noAssignmentString = "No Assignment exists with id " + idString;

        if(assignmentOptional.isEmpty()) {
            model.addAttribute("noAssignment", noAssignmentString);
            return "assignmentDisplay";
        }

        Assignment assignment = new Assignment();

        if (assignmentOptional.isPresent())
            assignment = assignmentOptional.get();

        if (assignment.getCodeReviewer() != null)
            model.addAttribute("codeReviewer");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);

        String usernameVal = assignment.getUser().getUsername();


//        if (user.isPresent())
//            model.addAttribute("username", user.get().getUsername());

        if (user.isPresent()) {

            if (tokenChecker.checkIfRoleReviewer(user.get()))
                model.addAttribute("codereviewer", user.get().getUsername());
            else
                model.addAttribute("username", user.get().getUsername());
        }



        if(user.isPresent() && assignment.getUser().getUsername().equals(user.get().getUsername())) {
            model.addAttribute("noAssignment", "This assignment may be edited and resubmitted");
            model.addAttribute("assignment", assignment);
            return "assignmentEdit";
        }

        else {
            model.addAttribute("noAssignment", "This assignment belongs to " + usernameVal);
            model.addAttribute("assignment", assignment);
            if (assignment.getCodeReviewer() != null)
                model.addAttribute("codeReviewer", assignment);
            return "assignmentDisplay";
        }


    }

    @RequestMapping(value = "/api/assignments", method = RequestMethod.POST)
    public String submitAssignment(Model model, @RequestParam String username, @RequestParam String branch,
                                   @RequestParam String githubUrl, @RequestParam String reviewVideoUrl) {

        User user = userRepository.findByUsername(username).orElseThrow();


            Assignment assignment = new Assignment("IN_REVIEW", branch, reviewVideoUrl, githubUrl, user);
            assignmentRepository.save(assignment);


        return showDashboard(model);
    }


}
