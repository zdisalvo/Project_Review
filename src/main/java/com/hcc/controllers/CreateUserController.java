package com.hcc.controllers;

import com.hcc.entities.Authority;
import com.hcc.entities.User;
import com.hcc.repositories.AuthorityRepository;
import com.hcc.repositories.AuthorityRepository;
import com.hcc.repositories.UserRepository;
import com.hcc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class CreateUserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @RequestMapping(value = "/createuser", method = RequestMethod.GET)
    public String showCreateUserPage(ModelMap map) {

        return "createuser";
    }

    @RequestMapping(value = "/createuser", method = RequestMethod.POST)
    @Transactional
    public String createUser(Model model, @RequestParam String username, @RequestParam String password, @RequestParam(value = "authorities") List<String> authorities) {

        if (userService.userExists(username)){
            model.addAttribute("error", true);
            return "createuser";
        }

        User user = new User(new Date(), username, password);

        List<Authority> authorityList = new ArrayList<>();
        for (String a : authorities) {
            Authority auth = new Authority(a, user);
            authorityRepository.save(auth);
            authorityList.add(auth);
        }
        user.setAuthorities(authorityList);

        userService.saveUser(user);

        return "index";
    }
}
