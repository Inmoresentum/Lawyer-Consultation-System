package com.cse471.project.repository;

import com.cse471.project.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
    @Query("SELECT distinct faq FROM FAQ faq" +
            " WHERE LOWER(faq.question) LIKE" +
            " LOWER(CONCAT('%', :searchTerm, '%'))" +
            " OR LOWER(faq.answer)" +
            " LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    @Transactional
    List<FAQ> getFAQsBySearchTerm(String searchTerm);

    boolean existsByQuestion(String faqQuestion);

}
