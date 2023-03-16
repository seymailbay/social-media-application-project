package com.seyma.socialmediaapplication.controllers;

import com.seyma.socialmediaapplication.model.Like;
import com.seyma.socialmediaapplication.requests.LikeCreateRequest;
import com.seyma.socialmediaapplication.responses.LikeResponse;
import com.seyma.socialmediaapplication.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/Likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId,
                                          @RequestParam Optional<Long> postId){
        return likeService.getAllLikesById(userId,postId);
    }

    @PostMapping
    public Like createOneLike(@RequestBody LikeCreateRequest request){
        return likeService.createOneLike(request);
    }

    @GetMapping("/{likeId}")
    public Like getOneLike(@PathVariable Long likeId){
        return likeService.getOneLikeById(likeId);
    }

    @DeleteMapping("/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId){
        likeService.deleteOneLike(likeId);
    }


}
