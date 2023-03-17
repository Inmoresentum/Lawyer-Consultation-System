package com.cse471.project.repository;

import com.cse471.project.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQRepo extends JpaRepository<FAQ, Long> {
    List<FAQ> findFAQSByQuestionContainingIgnoreCase(String searchTera);
}
