package com.hcc.controllers;

import com.hcc.services.UserDetailServiceImpl;
import com.hcc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.UsesSunMisc;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ValidationController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @RequestMapping(value = "api/auth/validate", method = RequestMethod.GET)
    public boolean validateToken(Model model, @RequestParam String token, @RequestParam String username) {

        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        return jwtUtil.validateToken(token, userDetails);
    }
}
