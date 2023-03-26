package com.cse471.project.entity;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reviewId;

    @Column(columnDefinition = "TEXT", name = "lawyer_review")
    private String review;

    @ManyToOne
    private Lawyer lawyer;
    private double reviewRating;
}
