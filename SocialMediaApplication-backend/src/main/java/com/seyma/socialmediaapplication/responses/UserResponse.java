package com.seyma.socialmediaapplication.responses;

import com.seyma.socialmediaapplication.model.User;
import lombok.Data;

@Data
public class UserResponse {

    Long id;
    int avatarId;
    String userName;

    public UserResponse(User model){
        this.id=model.getId();
        this.avatarId=model.getAvatar();
        this.userName=model.getUsername();
    }

}
