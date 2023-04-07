package com.cse471.project.service.Lawyer;

import com.cse471.project.entity.User;
import com.cse471.project.repository.LawyerRepository;
import com.cse471.project.repository.LawyerRoleApplicationRepository;
import com.cse471.project.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LawyerRoleApplicationService {
    private final LawyerRepository lawyerRepository;
    private final UserRepository userRepository;
    private final LawyerRoleApplicationRepository lawyerRoleApplicationRepository;

    public LawyerRoleApplicationService(LawyerRepository lawyerRepository,
                                        UserRepository userRepository,
                                        LawyerRoleApplicationRepository
                                                lawyerRoleApplicationRepository) {
        this.lawyerRepository = lawyerRepository;
        this.userRepository = userRepository;
        this.lawyerRoleApplicationRepository = lawyerRoleApplicationRepository;
    }

    public void saveLawyerApplication(com.cse471.project.entity.LawyerRoleApplication lawyerRoleApplication) {
        lawyerRoleApplicationRepository.save(lawyerRoleApplication);
    }

    public void deleteLawyerApplication(com.cse471.project.entity.LawyerRoleApplication lawyerRoleApplication) {
        lawyerRoleApplicationRepository.delete(lawyerRoleApplication);
    }

    public boolean containsPendingApplication(User user) {
        return lawyerRoleApplicationRepository.existsByUser(user);
    }
}
