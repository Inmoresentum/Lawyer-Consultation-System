package com.cse471.project.repository;

import com.cse471.project.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepo extends JpaRepository<FAQ, Long> {
}
