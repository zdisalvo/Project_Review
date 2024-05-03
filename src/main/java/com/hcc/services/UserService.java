package com.hcc.services;

import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

//    @Autowired
//    private PasswordEncoder passwordEncoder;



//    public void saveUser(User user) {
//        // Encode the password before storing it
//        String password = user.getPassword();
//        String encodedPassword = passwordEncoder.encode(password);
//        user.setPassword(encodedPassword);
//        userRepository.save(user);
//    }

    public void saveUser(User user) {
        // Encode the password before storing it
        String password = user.getPassword();
        String encodedPassword = customPasswordEncoder.getPasswordEncoder().encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }


    @Transactional
    public boolean validateUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if(user == null)
            return false;

        String encodedPassword = user.getPassword();

        if (customPasswordEncoder.getPasswordEncoder().matches(rawPassword, encodedPassword)) {
            user.getAuthorities();
            return true;
        }

        return false;
    }

    public boolean userExists(String username) {
        if(userRepository.findByUsername(username).isEmpty())
            return false;

        return true;
    }

}

