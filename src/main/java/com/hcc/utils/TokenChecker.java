package com.hcc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class TokenChecker {

    @Autowired
    JwtUtil jwtUtil;

    public Model addTokenAttributes(Model model, String token) {

        if(jwtUtil.isTokenExpired(token)) {
            model.addAttribute("error", "Please login again");
            return model;
        }

        model.addAttribute("token", token);
        model.addAttribute("username", jwtUtil.getUsernameFromToken(token));

        return model;
    }
}
