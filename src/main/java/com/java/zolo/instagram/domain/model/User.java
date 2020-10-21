package com.java.zolo.instagram.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String userName;

    @Column(name = "email_id", unique = true, nullable = false)
    private String emailId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_name", nullable = false)
    private String profileName;

    @Column(name = "bio")
    private String bio;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "contact_no")
    private String contactNo;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "posts")
    private List<Posts> postsList;*/

}
