package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

        @Autowired
        private PostRepo postRepo;

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private CategoryRepo categoryRepo;

        @Autowired
        private UserRepo userRepo;

        @Override
        public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {

                Category cat = this.categoryRepo.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "category Id",
                                                categoryId));

                User user = this.userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

                Post post = this.modelMapper.map(postDto, Post.class);
                post.setImage("default.png");
                post.setDate(new Date());
                post.setUser(user);
                
                post.setCategory(cat);

                return this.modelMapper.map(this.postRepo.save(post), PostDto.class);

        }

        @Override
        public PostDto updatePost(PostDto postDto, Integer PostId) {
                Post post = this.postRepo.findById(PostId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", PostId));
                post.setTitle(postDto.getTitle());
                post.setContent(postDto.getContent());
                post.setImage(postDto.getImage());
                return this.modelMapper.map(this.postRepo.save(post), PostDto.class);

        }

        @Override
        public PostDto getPostById(Integer PostId) {
                Post post = this.postRepo.findById(PostId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", PostId));
                return this.modelMapper.map(post, PostDto.class);
        }

        @Override
        public void deletePost(Integer PostId) {
                Post post = this.postRepo.findById(PostId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", PostId));
                this.postRepo.delete(post);
        }

        @Override
        public PostResponse getAllPostByUser(Integer userId, Integer pageSize, Integer pageNumber) {
                Pageable pageable = PageRequest.of(pageNumber, pageNumber);
                User user = this.userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
                Page<Post> pagePost = this.postRepo.findByUser(user, pageable);
                List<Post> posts = pagePost.getContent();
                List<PostDto> dtoList = posts.stream().map(p -> this.modelMapper.map(p, PostDto.class))
                                .collect(Collectors.toList());
                PostResponse postResponse = new PostResponse();
                postResponse.setContent(dtoList);
                postResponse.setPageNumber(pagePost.getNumber());
                postResponse.setPageSize(pagePost.getSize());
                postResponse.setTotalElements(pagePost.getTotalElements());
                postResponse.setLastPage(pagePost.isLast());
                return postResponse;
        }

        @Override
        public PostResponse getAllPostByCategory(Integer categoryId, Integer pageSize, Integer pageNumber) {
                Pageable pageable = PageRequest.of(pageNumber, pageNumber);
                Category cat = this.categoryRepo.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "category Id",
                                                categoryId));
                Page<Post> pagePost = this.postRepo.findByCategory(cat, pageable);
                List<Post> posts = pagePost.getContent();
                List<PostDto> dtoList = posts.stream().map(p -> this.modelMapper.map(p, PostDto.class))
                                .collect(Collectors.toList());
                PostResponse postResponse = new PostResponse();
                postResponse.setContent(dtoList);
                postResponse.setPageNumber(pagePost.getNumber());
                postResponse.setPageSize(pagePost.getSize());
                postResponse.setTotalElements(pagePost.getTotalElements());
                postResponse.setLastPage(pagePost.isLast());
                return postResponse;
        }

        @Override
        public List<PostDto> searchPosts(String keyword) {
                //List<Post> posts1 = this.postRepo.searchByTitle("%" + keyword + "%");

                List<Post> posts = this.postRepo.findByTitleContaining(keyword);
                List<PostDto> dtoList = posts.stream().map(p -> this.modelMapper.map(p, PostDto.class))
                                .collect(Collectors.toList());
                return dtoList;
        }

        @Override
        public PostResponse getAllPosts(Integer pageSize, Integer pageNumber, String sortBy, String sortDir) {
                Sort s = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
                Pageable p = PageRequest.of(pageSize, pageNumber, s);
                Page<Post> pagePost = this.postRepo.findAll(p);
                List<Post> posts = pagePost.getContent();
                List<PostDto> dtoList = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
                                .collect(Collectors.toList());

                PostResponse postResponse = new PostResponse();
                postResponse.setContent(dtoList);
                postResponse.setPageNumber(pagePost.getNumber());
                postResponse.setPageSize(pagePost.getSize());
                postResponse.setTotalElements(pagePost.getTotalElements());
                postResponse.setLastPage(pagePost.isLast());
                return postResponse;
        }

}
