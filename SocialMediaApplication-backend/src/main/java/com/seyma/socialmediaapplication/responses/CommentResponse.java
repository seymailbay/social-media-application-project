package com.seyma.socialmediaapplication.responses;

import com.seyma.socialmediaapplication.model.Comment;
import lombok.Data;

@Data
public class CommentResponse {

    Long id;
    Long userId;
    String userName;
    String text;

    public CommentResponse(Comment model){
        this.id =model.getId();
        this.userId=model.getUser().getId();
        this.userName= model.getUser().getUsername();
        this.text=model.getText();
    }
}
