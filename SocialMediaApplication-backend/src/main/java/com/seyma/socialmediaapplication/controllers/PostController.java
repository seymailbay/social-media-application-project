package com.seyma.socialmediaapplication.controllers;

import com.seyma.socialmediaapplication.model.Post;
import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.requests.PostCreateRequest;
import com.seyma.socialmediaapplication.requests.PostUpdateRequest;
import com.seyma.socialmediaapplication.responses.PostResponse;
import com.seyma.socialmediaapplication.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPost(@RequestParam Optional<Long> userId){
        return postService.getAllPost(userId);
    }

    @GetMapping("/{postId}")
    public Post getOnePost(@PathVariable Long postId){
        return postService.getOnePostById(postId);
    }

    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest){
        return postService.createOnePost(newPostRequest);
    }

    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest updateRequest){
        return postService.updateOnePostById(postId,updateRequest);
    }

    @DeleteMapping("/{postId}")
    public void deleteOnePost(@PathVariable Long postId){
        postService.deleteOnePost(postId);
    }
}
