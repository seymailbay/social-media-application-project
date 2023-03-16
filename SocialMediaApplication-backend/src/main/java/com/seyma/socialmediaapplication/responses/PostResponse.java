package com.seyma.socialmediaapplication.responses;

import com.seyma.socialmediaapplication.model.Like;
import com.seyma.socialmediaapplication.model.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    Long id;
    Long userId;
    String userName;
    String title;
    String text;
    List<LikeResponse> postLikes;

    public PostResponse(Post model, List<LikeResponse> likes){
        this.id=model.getId();
        this.userId=model.getUser().getId();
        this.userName=model.getUser().getUsername();
        this.title=model.getTitle();
        this.text=model.getText();
        this.postLikes=likes;
    }
}
