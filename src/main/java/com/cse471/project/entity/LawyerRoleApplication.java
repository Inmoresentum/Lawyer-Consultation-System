package com.cse471.project.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class LawyerRoleApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "lawyeridgenerator")
    @SequenceGenerator(name = "lawyeridgenerator", initialValue = 100)
    private Long applicationID;
    @Column(nullable = false, name = "application_submission_time")
    private LocalDateTime submittedAt;
    @OneToOne
    private User user;
    @Column(name = "why_join_here", nullable = false, columnDefinition = "TEXT")
    private String includedApplicationMotivation;

    @Column(name = "bio_for_lawyer_profile", nullable = false, columnDefinition = "TEXT")
    private String bioForLawyerProfile;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    private Set<LawyerType> selectedLawyerType;
    @Lob
    @Column(length = 10000000, name = "included_document")
    private byte[] includedDocuments;
}
