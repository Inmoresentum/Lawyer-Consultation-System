package com.cse471.project.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reviewId;
    @Column(nullable = false, columnDefinition = "TEXT", name = "lawyer_review")
    private String review;
    @OneToOne
    @NotNull
    private Lawyer lawyer;
    @ManyToOne
    @JoinColumn(nullable = false, name = "the_user_who_reviewed")
    User user;
    @Column(nullable = false, name = "given_rating_by_the_user")
    private double reviewRating;
}
