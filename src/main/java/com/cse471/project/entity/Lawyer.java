package com.cse471.project.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
}
