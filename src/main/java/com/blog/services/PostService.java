package com.blog.services;

import java.util.List;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer categoryId, Integer userId);

    PostDto updatePost(PostDto postDto, Integer PostId);

    PostDto getPostById(Integer PostId);

    PostResponse getAllPosts(Integer pageSize, Integer pageNumber, String sortBy,String sortDir);

    void deletePost(Integer PostId);

    PostResponse getAllPostByUser(Integer userId, Integer pageSize, Integer pageNumber);

    PostResponse getAllPostByCategory(Integer categoryId, Integer pageSize, Integer pageNumber);

    List<PostDto> searchPosts(String keyword);
}
