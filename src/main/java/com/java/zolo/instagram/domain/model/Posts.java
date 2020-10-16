package com.java.zolo.instagram.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
@Accessors(chain = true)
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    /*@OneToMany(orphanRemoval = true)  // here mapped by won't appear
    @JoinColumn(name = "fk_image_urls", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Images> imageURLs;*/

    /*@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "likes")
    private List<Likes> postLikes;*/ // if I remove mappedBy, creates a new table
}


