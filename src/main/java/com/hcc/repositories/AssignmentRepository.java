package com.hcc.repositories;

import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.enums.AssignmentStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    public Optional<Assignment> findAssignmentById(long id);

    public Optional<List<Assignment>> findAssignmentsByUserAndStatus(Optional<User> user, String status);


    public Optional<List<Assignment>> findAssignmentsByStatus(String status);

}
