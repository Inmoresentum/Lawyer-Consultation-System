package com.cse471.project.service.UserService;

import com.cse471.project.entity.ForgotPasswordVerificationToken;
import com.cse471.project.entity.User;
import com.cse471.project.entity.UserVerificationToken;
import com.cse471.project.repository.UserForgotPasswordVerificationRepository;
import com.cse471.project.repository.UserRepository;
import com.cse471.project.repository.UserVerificationTokenRepository;
import com.cse471.project.service.Email.EmailService;
import com.cse471.project.service.Email.EmailUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserVerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailUtils emailUtils;
    private final EmailService emailService;
    private final UserForgotPasswordVerificationRepository forgotPasswordTokeRepo;

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Transactional
    public void registerUser(User user, String password) {
        user.setHashedPassword(passwordEncoder.encode(password));
        // Time to send the verification email
        userRepository.save(user);
        final String activationToken = UUID.randomUUID().toString();
        final String EMAIL_VERIFICATION_URL = "http://localhost:8080/activate?token=";
        final String activationLink = EMAIL_VERIFICATION_URL.concat(activationToken);
        // Also, have to save this activation token in the token repository.
        UserVerificationToken userVerificationToken = new UserVerificationToken(activationToken,
                LocalDateTime.now(), LocalDateTime.now().plusHours(3), user);
        // Saving the token
        verificationTokenRepository.save(userVerificationToken);
        emailService.send(user.getEmail(), "Account Activation", emailUtils
                .buildConfirmationEmail(user.getName(), activationLink));
    }

    public void sendForgotPasswordResetLink(String emailAddress) {
        final String forgotPasswordVerificationToken = UUID.randomUUID().toString();
        final String EMAIL_VERIFICATION_URL = "http://localhost:8080/forgot-pass-verify?token=";
        final String resetLink = EMAIL_VERIFICATION_URL.concat(forgotPasswordVerificationToken);
        var user = userRepository.findByEmail(emailAddress);
        if (user.isEmpty()) throw new IllegalStateException("User details must need to be in the " +
                "database");
        emailService.send(user.get().getEmail(), "Password Resetting Link",
                emailUtils.buildEmailPasswordResetRequest(user.get().getName(), resetLink));
        ForgotPasswordVerificationToken verificationToken = new ForgotPasswordVerificationToken();
        verificationToken.setToken(forgotPasswordVerificationToken);
        verificationToken.setUser(user.get());
        verificationToken.setCreatedAt(LocalDateTime.now());
        verificationToken.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        forgotPasswordTokeRepo.save(verificationToken);
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

    public Optional<User> findByUserName(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findById(Long.parseLong(userId));
    }

    @Transactional
    public void changeUserAccountPassword(User user) {
        try {
            update(user);
            String accountRecoveryLink = "http://localhost:8080/forgot-password";
            var htmlEmail = emailUtils.buildEmailPasswordChangeNotifier(
                    user.getUsername(), accountRecoveryLink);
            emailService.send(user.getEmail(),
                    "Account Password Got changed", htmlEmail);
        } catch (Exception e) {
            throw new IllegalStateException("Can't update the user");
        }
    }

    public Optional<User> getCurrentLoggedInUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        var user = findByUserName(userDetails.getUsername());
        if (user.isEmpty()) throw new IllegalStateException("Currently logged in user can't be empty");
        return user;
    }
}
