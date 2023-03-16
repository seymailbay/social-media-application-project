package com.seyma.socialmediaapplication.services;

import com.seyma.socialmediaapplication.model.Comment;
import com.seyma.socialmediaapplication.model.Post;
import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.repository.CommentRepo;
import com.seyma.socialmediaapplication.requests.CommentCreateRequest;
import com.seyma.socialmediaapplication.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepo commentRepo;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepo commentRepo,UserService userService,PostService postService) {
        this.commentRepo = commentRepo;
        this.userService= userService;
        this.postService=postService;
    }

    public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        if(userId.isPresent() && postId.isPresent()){
            return commentRepo.findByUserIdAndPostId(userId.get(),postId.get());
        }
        else if(userId.isPresent()){
            return commentRepo.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return commentRepo.findByPostId(postId.get());
        }
        else
            return commentRepo.findAll();
    }


    public Comment getOneCommentById(Long commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest request) {

        User user= userService.getOneUserById(request.getUserId());
        Post post=postService.getOnePostById(request.getPostId());

        if(user != null && post !=null){
            Comment commentToSave=new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setText(request.getText());
            commentToSave.setCreateDate(new Date());
            return commentRepo.save(commentToSave);
        }
        else
            return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment=commentRepo.findById(commentId);
        if(comment.isPresent()){
            Comment commentToUpdate =comment.get();
            commentToUpdate.setText(commentUpdateRequest.getText());
            return commentRepo.save(commentToUpdate);
        }
        else
            return null;
    }

    public void deleteById(Long commentId) {
        commentRepo.deleteById(commentId);
    }
}
