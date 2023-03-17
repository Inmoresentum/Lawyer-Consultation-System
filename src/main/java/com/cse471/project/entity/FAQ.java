package com.cse471.project.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "FAQ")
@ToString
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    // The initial value is to account for data.sql demo data ids
    @SequenceGenerator(name = "idgenerator", initialValue = 50)
    private Long id;
    @Version
    private int version;
    @Column(nullable = false, name = "faq_question")
    private String question;
    @Column(nullable = false, name = "faq_answer", columnDefinition = "TEXT")
    private String answer;
    @CreatedDate
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime faqCreatedAt;
    @CreatedDate
    @Column(columnDefinition = "DATETIME")
    private LocalDateTime faqModifiedAt;
}
