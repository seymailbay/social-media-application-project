package com.seyma.socialmediaapplication.repository;

import com.seyma.socialmediaapplication.model.Post;
import com.seyma.socialmediaapplication.responses.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long user_id);
}
