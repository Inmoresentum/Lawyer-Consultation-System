package com.cse471.project.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Article")
@EqualsAndHashCode
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "article_title")
    private String articleTile;
    @Column(columnDefinition = "TEXT", name = "article_content")
    private String content;
    @ManyToOne
    private Lawyer author;
    @Lob
    @Column(length = 1000000)
    @Nullable
    private byte[] relatedLegalForm;
}
