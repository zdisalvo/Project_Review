package com.hcc.repositories;

import com.hcc.entities.Authority;
import com.hcc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

//    public List<Authority> findByAuthority(List<String> authorities);


}