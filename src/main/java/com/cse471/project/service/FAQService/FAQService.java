package com.cse471.project.service.FAQService;

import com.cse471.project.entity.FAQ;
import com.cse471.project.repository.FAQRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQService {
    private final FAQRepository faqRepo;

    public FAQService(FAQRepository faqRepo) {
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

    public boolean faqQuestionAlreadyExists(String faqQuestion) {
        return faqRepo.existsByQuestion(faqQuestion);
    }
}
