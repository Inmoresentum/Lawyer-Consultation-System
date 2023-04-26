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
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Enumerated(EnumType.STRING)
    private IncludedDocumentType includedDocumentType;
    @Column(name = "Application_got_approved")
    private boolean isApproved;
    @Column(name = "Application_got_reviewed")
    private boolean isReviewed = false;
    @Column(name = "When_the_form_was_reviewd_by_the_admin")
    private LocalDateTime reviewedTime;

    @OneToOne
    private User reviewer;
    @Column(name = "comment_by_reviewer", columnDefinition = "TEXT")
    private String commentByReviewer;
}
