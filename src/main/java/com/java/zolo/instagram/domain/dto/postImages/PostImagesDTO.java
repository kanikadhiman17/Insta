package com.java.zolo.instagram.domain.dto.postImages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PostImagesDTO {
    private String caption;
    private String location;
    private List<String> imageURLs;
}
