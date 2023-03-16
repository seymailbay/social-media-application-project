package com.seyma.socialmediaapplication.services;

import com.seyma.socialmediaapplication.model.Like;
import com.seyma.socialmediaapplication.model.Post;
import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.repository.LikeRepo;
import com.seyma.socialmediaapplication.requests.LikeCreateRequest;
import com.seyma.socialmediaapplication.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    LikeRepo likeRepo;
    UserService userService;

    PostService postService;

    public LikeService(LikeRepo likeRepo,UserService userService,PostService postService) {
        this.likeRepo = likeRepo;
        this.userService=userService;
        this.postService=postService;
    }

    public List<LikeResponse> getAllLikesById(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()){
            list= likeRepo.findByUserIdAndPostId(userId.get(),postId.get());
        } else if (userId.isPresent()) {
            list= likeRepo.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            list= likeRepo.findByPostId(postId.get());
        }
        else
            list= likeRepo.findAll();
        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());

    }

    public Like createOneLike(LikeCreateRequest request) {
        User user=userService.getOneUserById(request.getUserId());
        Post post=postService.getOnePostById(request.getPostId());
        if(user !=null && post != null){
            Like likeToSave =new Like();
            likeToSave.setId(request.getId());
            likeToSave.setPost(post);
            likeToSave.setUser(user);
            return likeRepo.save(likeToSave);
        }
        else
            return null;
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepo.findById(likeId).orElse(null);
    }

    public void deleteOneLike(Long likeId) {
        likeRepo.deleteById(likeId);
    }

}
