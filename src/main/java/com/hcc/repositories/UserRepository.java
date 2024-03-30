package com.hcc.repositories;

import com.hcc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    public Optional<User> findByUsername(String username);

    public User getUserById(Long id);

    public User save(User user);


}
