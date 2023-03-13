package com.cse471.project.service.FAQService;

import com.cse471.project.entity.FAQ;
import com.cse471.project.repository.FAQRepo;
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
}
