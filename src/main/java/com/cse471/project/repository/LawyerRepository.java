package com.cse471.project.repository;

import com.cse471.project.entity.Lawyer;
import com.cse471.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
    Lawyer findByUser(User user);
}
