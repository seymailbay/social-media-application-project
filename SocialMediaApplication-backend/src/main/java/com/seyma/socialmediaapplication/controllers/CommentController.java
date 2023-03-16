package com.seyma.socialmediaapplication.controllers;

import com.seyma.socialmediaapplication.model.Comment;
import com.seyma.socialmediaapplication.requests.CommentCreateRequest;
import com.seyma.socialmediaapplication.requests.CommentUpdateRequest;
import com.seyma.socialmediaapplication.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return commentService.getAllCommentsWithParam(userId,postId);
    }

    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneCommentById(commentId);
    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest request){
        return commentService.createOneComment(request);
    }
    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId,@RequestBody CommentUpdateRequest commentUpdateRequest){
        return commentService.updateOneCommentById(commentId,commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId){
        commentService.deleteById(commentId);
    }


}
