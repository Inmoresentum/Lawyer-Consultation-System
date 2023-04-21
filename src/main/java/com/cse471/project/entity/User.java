package com.cse471.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class User extends AbstractEntity {
    @Size(max = 12, min = 4)
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String name;
    @JsonIgnore
    @Column(nullable = false)
    @Size(max = 356, min = 4)
    private String hashedPassword;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    private Set<Role> roles;
    @Lob
    @Column(length = 1000000)
    @Nullable
    private byte[] profilePicture;
    @Nullable
    //Todo: before deployment must need
    // to add proper validation
    @Size(min = 4, max = 34)
    private String phoneNumber;
    @Size(min = 5, max = 65)
    @Column(nullable = false, unique = true)
    @Email
    private String email;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private boolean accountVerified = false;
    private boolean deactivatedByAdmin = false;
    @Column(columnDefinition = "TEXT")
    private String aboutYourSelf;
    @Column(name = "user_address")
    @Nullable
    private String address;

    @Column(name = "user_gender")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @CreatedDate
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime accountCreated;
    @Column(nullable = false, name = "Privacy_Policy_And_TOS_Agreement")
    private boolean agreesWithTermsOfServicesAndPrivacyAndPolicy;

    private boolean appliedForLawyerRole = false;
    private int appliedNumberOfTimesForLawyerRole = 0;

    @Enumerated(EnumType.STRING)
    private LawyerRoleApplicationStatus lawyerRoleApplicationStatus;
    private LocalDateTime lastUpdateTime;
}
