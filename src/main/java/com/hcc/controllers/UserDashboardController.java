package com.hcc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserDashboardController {

    @RequestMapping(value = "/mydashboard", method = RequestMethod.GET)
    String showDashboard(ModelMap map) {
        return "mydashboard";
    }

}
