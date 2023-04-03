package com.cse471.project.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LawyerRoleApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "lawyeridgenerator")
    @SequenceGenerator(name = "lawyeridgenerator", initialValue = 100)
    private Long applicationID;
    @Column(name = "application_submission_time")
    private LocalDateTime submittedAt;
    @OneToOne
    private User user;
    @Column(name = "why_join_here", nullable = false, columnDefinition = "TEXT")
    private String includedApplicationBio;

    @Column(name = "bio_for_lawyer_profile", nullable = false, columnDefinition = "TEXT")
    private String bioForLawyerProfile;
    @Lob
    @Column(length = 10000000, name = "included_document")
    private byte[] includedDocuments;
}
