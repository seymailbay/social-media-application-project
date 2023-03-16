package com.seyma.socialmediaapplication.requests;

import com.seyma.socialmediaapplication.model.User;
import lombok.Data;

@Data
public class PostCreateRequest {

    Long id;
    String text;
    String title;
    Long userId;

}
