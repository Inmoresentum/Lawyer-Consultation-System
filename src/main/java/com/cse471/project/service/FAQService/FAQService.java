package com.cse471.project.service.FAQService;

import com.cse471.project.entity.FAQ;
import com.cse471.project.repository.FAQRepo;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQService {
    private final FAQRepo faqRepo;

    public FAQService(FAQRepo faqRepo) {
        this.faqRepo = faqRepo;
    }

    public List<FAQ> findAllFaq() {
        return faqRepo.findAll();
    }

    public void update(FAQ faq) {
        try {
            faqRepo.save(faq);
        } catch (ObjectOptimisticLockingFailureException ex) {
            FAQ existingFAQ = faqRepo.findById(faq.getId()).orElse(null);
            if (existingFAQ == null) {
                throw new RuntimeException("FAQ not found: " + faq.getId());
            }
            existingFAQ.setQuestion(faq.getQuestion());
            existingFAQ.setAnswer(faq.getAnswer());
            update(existingFAQ);
        }
    }

    public void delete(FAQ faq) {
        faqRepo.delete(faq);
    }

    public void postNewFaq(FAQ faq) {
        faqRepo.save(faq);
    }

    public List<FAQ> findAllFAQsContainingSearchTerm(String searchTerm) {
        return faqRepo.getFAQsBySearchTerm(searchTerm);
    }
}
