package com.myblog8.entity;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name="post", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",  nullable = false)
    private  String title;

    @Column(name="description",  nullable = false)
    private  String description;
    @Column(name="content",  nullable = false)
    private  String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Comment> comments = new HashSet<>();
}
