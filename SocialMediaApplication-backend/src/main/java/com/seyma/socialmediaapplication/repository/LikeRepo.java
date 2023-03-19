package com.seyma.socialmediaapplication.repository;

import com.seyma.socialmediaapplication.model.Comment;
import com.seyma.socialmediaapplication.model.Like;
import com.seyma.socialmediaapplication.responses.LikeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Like,Long> {
    List<Like> findAllById(Long userId);

    List<Like> findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByUserId(Long userId);

    List<Like> findByPostId(Long postId);


    @Query(value = "select 'liked', l.post_id, u.avatar, u.username from "
                    + "p_like l left join users u on u.id=l.user_id "
                    + "where l.post_id in :postIds limit 5", nativeQuery = true)
    List<Object> findUserLikesByPostId(@Param("postIds") List<Long> postIds);

}
