package com.seyma.socialmediaapplication.services;

import com.seyma.socialmediaapplication.model.Like;
import com.seyma.socialmediaapplication.model.Post;
import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.repository.LikeRepo;
import com.seyma.socialmediaapplication.repository.PostRepo;
import com.seyma.socialmediaapplication.requests.PostCreateRequest;
import com.seyma.socialmediaapplication.requests.PostUpdateRequest;
import com.seyma.socialmediaapplication.responses.LikeResponse;
import com.seyma.socialmediaapplication.responses.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepo postRepo; //post repoya bağladık.

    private LikeService likeService;
    private UserService userService;

    public PostService(PostRepo postRepo,UserService userService) {
        this.postRepo = postRepo;
        this.userService=userService;
    }
    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPost(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent()){
            list = postRepo.findByUserId(userId.get());
        }
        else{
            list =postRepo.findAll();
            }
        return list.stream().map(p ->{
            List<LikeResponse> likes= likeService.getAllLikesById(Optional.ofNullable(null), Optional.of(p.getId()));
            return new PostResponse(p,likes);
        }).collect(Collectors.toList());
    }

    public Post createOnePost(PostCreateRequest newPostCreateRequest){
        User user =userService.getOneUserById(newPostCreateRequest.getUserId());
        if(user==null)
            return null;
        Post toSave =new Post();
        toSave.setId(newPostCreateRequest.getId());
        toSave.setText(newPostCreateRequest.getText());
        toSave.setTitle(newPostCreateRequest.getTitle());
        toSave.setUser(user);
        toSave.setCreateDate(new Date());
        return postRepo.save(toSave);
    }

    public Post getOnePostById(Long postId) {
        return postRepo.findById(postId).orElse(null);
    }

    public PostResponse getOnePostByIdWithLikes(Long postId){
        Post post =postRepo.findById(postId).orElse(null);
        List<LikeResponse> likes =likeService.getAllLikesById(Optional.ofNullable(null),Optional.of(post.getId()));
        return new PostResponse(post,likes);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest updateRequest) {
        Optional<Post> post=postRepo.findById(postId);
        if(post.isPresent()){
            Post toUpdate=post.get();
            toUpdate.setText(updateRequest.getText());
            toUpdate.setTitle(updateRequest.getTitle());
            postRepo.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOnePost(Long postId) {
        postRepo.deleteById(postId);
    }
}
