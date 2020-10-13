package com.java.zolo.instagram.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.List;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    @Column(name = "caption")
    private String caption;

    @Column(name = "location")
    private String location;

    @CreationTimestamp
    @Column(name = "posted_at")
    private Timestamp postedAt;

    @UpdateTimestamp
    @Column(name = "edited_at")
    private Timestamp editedAt;

    @OneToMany(orphanRemoval = true)  // here mapped by won't appear
    @JoinColumn(name = "fk_image_urls", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Images> imageURLs;

    /*@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "likes")
    private List<Likes> postLikes;*/ // Not working... and if I remove mappedBy, creates a new table
}


