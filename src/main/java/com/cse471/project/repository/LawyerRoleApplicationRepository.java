package com.cse471.project.repository;

import com.cse471.project.entity.LawyerRoleApplication;
import com.cse471.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawyerRoleApplicationRepository
        extends JpaRepository<LawyerRoleApplication, Long> {
    boolean existsByUser(User user);
}
