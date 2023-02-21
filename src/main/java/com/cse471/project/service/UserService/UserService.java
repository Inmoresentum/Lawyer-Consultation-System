package com.cse471.project.service.UserService;

import com.cse471.project.entity.User;
import com.cse471.project.entity.VerificationToken;
import com.cse471.project.repository.UserRepository;
import com.cse471.project.repository.VerificationTokenRepository;
import com.cse471.project.service.Email.EmailService;
import com.cse471.project.service.Email.EmailUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailUtils emailUtils;
    private final EmailService emailService;

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public User update(User entity) {
        return userRepository.save(entity);
    }

    public void registerUser(User user, String password) {
        user.setHashedPassword(passwordEncoder.encode(password));
        // Time to send the verification email
        userRepository.save(user);
        final String activationToken = UUID.randomUUID().toString();
        final String EMAIL_VERIFICATION_URL = "http://localhost:8080/activate?token=";
        final String activationLink = EMAIL_VERIFICATION_URL.concat(activationToken);
        // Also, have to save this activation token in the token repository.
        VerificationToken verificationToken = new VerificationToken(activationToken,
                LocalDateTime.now(), LocalDateTime.now().plusHours(3), user);
        // Saving the token
        verificationTokenRepository.save(verificationToken);
        System.out.println(activationLink);
        System.out.println(activationToken);
        System.out.println(user);
        emailService.send(user.getEmail(), "Account Activation", emailUtils
                .buildConfirmationEmail(user.getName(), activationLink));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return userRepository.findAll(filter, pageable);
    }

    public int count() {
        return (int) userRepository.count();
    }

    public boolean findUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean userNameIsInUse(String username) {
        return userRepository.existsByUsername(username);
    }
}
