package com.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    // Custom Finder methods
    Page<Post> findByUser(User user, Pageable p);

    Page<Post> findByCategory(Category category, Pageable p);

    List<Post> findByTitleContaining(String title);

    
    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title);
}
