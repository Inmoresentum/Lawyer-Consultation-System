package com.cse471.project.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Lawyer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lawyer_id")
    private Long lawyerId;

    @ManyToOne
    private Review layerReview;
    @OneToOne
    private User user;
    @Column(columnDefinition = "TEXT", name = "lawyer_bio")
    private String lawyerBio;
    private double rating = 0.0;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    private Set<LawyerType> lawyerTypeSet;
}
