package com.java.zolo.instagram.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "image_url", nullable = false, unique = true)
    private String imageUrl;
}
