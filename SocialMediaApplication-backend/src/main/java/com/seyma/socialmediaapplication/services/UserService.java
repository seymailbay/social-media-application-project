package com.seyma.socialmediaapplication.services;


import com.seyma.socialmediaapplication.model.Comment;
import com.seyma.socialmediaapplication.model.Like;
import com.seyma.socialmediaapplication.model.Post;
import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.repository.CommentRepo;
import com.seyma.socialmediaapplication.repository.LikeRepo;
import com.seyma.socialmediaapplication.repository.PostRepo;
import com.seyma.socialmediaapplication.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepo userRepo;   // servis repoya bağlanır.
    LikeRepo likeRepo;
    CommentRepo commentRepo;
    PostRepo postRepo;

    public UserService(UserRepo userRepo,LikeRepo likeRepo,CommentRepo commentRepo,PostRepo postRepo) {
        this.userRepo = userRepo;
        this.likeRepo=likeRepo;
        this.commentRepo=commentRepo;
        this.postRepo=postRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User saveOneUser(User newUser) {
        return userRepo.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUsername(newUser.getUsername());
            foundUser.setPassword(newUser.getPassword());
            foundUser.setAvatar(newUser.getAvatar());
            userRepo.save(foundUser);
            return foundUser;
        } else
            return null;
    }

    public void deleteById(Long userId) {
        userRepo.deleteById(userId);
    }

    public User getOneUserByUserName(String username) {
        return userRepo.findByUsername(username);
    }

    public List<Object> getUserActivity(Long userId) {

        List<Long> postIds=postRepo.findTopPostsIdByUserId(userId); // top post idleri gelir.
        if(postIds.isEmpty())
            return null;

        List<Object> comments=commentRepo.findUserCommentsByPostId(postIds);
        List<Object> likes=likeRepo.findUserLikesByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;


    }
}
