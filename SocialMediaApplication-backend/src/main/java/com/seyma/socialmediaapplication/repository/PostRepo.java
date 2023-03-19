package com.seyma.socialmediaapplication.repository;

import com.seyma.socialmediaapplication.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userid);

    @Query( value = "select id from post where user_id= :userId order by create_date desc limit 5", nativeQuery = true)
    List<Long> findTopPostsIdByUserId(@Param("userId") Long userId);
}
