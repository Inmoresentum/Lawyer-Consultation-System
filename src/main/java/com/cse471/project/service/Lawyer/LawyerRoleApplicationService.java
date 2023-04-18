package com.cse471.project.service.Lawyer;

import com.cse471.project.entity.LawyerRoleApplication;
import com.cse471.project.entity.User;
import com.cse471.project.repository.LawyerRoleApplicationRepository;
import com.cse471.project.service.Email.EmailService;
import com.cse471.project.service.Email.EmailUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LawyerRoleApplicationService {
    private final LawyerRoleApplicationRepository lawyerRoleApplicationRepository;
    private final EmailUtils emailUtils;
    private final EmailService emailService;

    public LawyerRoleApplicationService(LawyerRoleApplicationRepository
                                                lawyerRoleApplicationRepository,
                                        EmailUtils emailUtils,
                                        EmailService emailService) {
        this.lawyerRoleApplicationRepository = lawyerRoleApplicationRepository;
        this.emailUtils = emailUtils;
        this.emailService = emailService;
    }

    public void saveLawyerApplication(LawyerRoleApplication lawyerRoleApplication, User user) {
        var htmlEmail = emailUtils.buildLawyerApplicationFormSuccessfulSubmissionEmail(user.getName());
        emailService.send(user.getEmail(), "Lawyer Role Application", htmlEmail);
        lawyerRoleApplicationRepository.save(lawyerRoleApplication);
    }

    public void deleteLawyerApplication(LawyerRoleApplication lawyerRoleApplication) {
        lawyerRoleApplicationRepository.delete(lawyerRoleApplication);
    }

    public boolean containsPendingApplication(User user) {
        return lawyerRoleApplicationRepository.existsByUser(user);
    }

    public List<LawyerRoleApplication> findAllLawyerApplicationYetTobeReviewed() {
        return lawyerRoleApplicationRepository.findByIsReviewedFalse();
    }

    public void updateLawyerApplication(LawyerRoleApplication lawyerRoleApplication) throws IllegalAccessException {
        if (lawyerRoleApplicationRepository.existsById(lawyerRoleApplication.getApplicationID())) {
            lawyerRoleApplicationRepository.save(lawyerRoleApplication);
        }
        throw new IllegalAccessException("The Application Must exists in Database");
    }
}
