package com.cse471.project.service.Lawyer;

import com.cse471.project.entity.Lawyer;
import com.cse471.project.repository.LawyerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LawyerService {
    private final LawyerRepository lawyerRepository;

    public LawyerService(LawyerRepository lawyerRepository) {
        this.lawyerRepository = lawyerRepository;
    }

    public void updateLawyer(Lawyer lawyer) {
        lawyerRepository.save(lawyer);
    }
}
