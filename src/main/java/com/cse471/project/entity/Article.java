package com.cse471.project.entity;

import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Article")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
