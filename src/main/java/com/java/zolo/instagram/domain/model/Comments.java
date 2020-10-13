package com.java.zolo.instagram.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY) // One post can have many comments
    @JoinColumn(name = "fk_post", nullable = false)
    private Posts post;

    @ManyToOne(fetch = FetchType.LAZY) // One user can comment many times
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "commented_at")
    private Timestamp commentedAt;

    @UpdateTimestamp
    @Column(name = "edited_at")
    private Timestamp editedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_comment") // Reply to a comment
    private Comments reply;

    /*@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "likes")
    private List<Likes> commentLikes;*/
}
