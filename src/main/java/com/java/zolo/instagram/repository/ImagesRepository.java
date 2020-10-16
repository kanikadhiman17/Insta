package com.java.zolo.instagram.repository;

import com.java.zolo.instagram.domain.model.Images;
import com.java.zolo.instagram.domain.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    List<Images> getAllByPost(Posts post);
}
