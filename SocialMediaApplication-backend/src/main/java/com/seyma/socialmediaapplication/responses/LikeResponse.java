package com.seyma.socialmediaapplication.responses;


import com.seyma.socialmediaapplication.model.Like;
import lombok.Data;

@Data
public class LikeResponse {

    Long id;
    Long userId;
    Long postId;

    public LikeResponse(Like model){
        this.id=model.getId();
        this.userId=model.getUser().getId();
        this. postId=model.getPost().getId();
    }
}
