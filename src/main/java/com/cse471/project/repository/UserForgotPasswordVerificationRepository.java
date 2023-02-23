package com.cse471.project.repository;

import com.cse471.project.entity.ForgotPasswordVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserForgotPasswordVerificationRepository
        extends JpaRepository<ForgotPasswordVerificationToken, Long> {
}
