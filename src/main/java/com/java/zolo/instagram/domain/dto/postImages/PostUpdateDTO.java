package com.java.zolo.instagram.domain.dto.postImages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDTO {
    private String location;
    private String caption;
}
