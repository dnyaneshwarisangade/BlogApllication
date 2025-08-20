package com.blog.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.blog.services.FileService;
import com.blog.config.AppConstants.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${project.image}")
    private String path;
    
    Logger log =org.slf4j.LoggerFactory.getLogger(PostController.class);

    @PostMapping("/user/{userId}/category/{catId}/post")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer catId) {
        PostDto newPostDto = this.postService.createPost(postDto, catId, userId);

        return new ResponseEntity<PostDto>(newPostDto, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMGER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
        PostResponse newPostDto = this.postService.getAllPostByUser(userId, pageSize, pageNumber);
        return new ResponseEntity<PostResponse>(newPostDto, HttpStatus.OK);
    }

    @GetMapping("/category/{catId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer catId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMGER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
        PostResponse newPostDto = this.postService.getAllPostByCategory(catId, pageSize, pageNumber);
        return new ResponseEntity<PostResponse>(newPostDto, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMGER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PostResponse postResponse = this.postService.getAllPosts(pageSize, pageNumber, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    ResponseEntity<PostDto> getPostByPostId(@PathVariable Integer postId) {
        PostDto PostDto = this.postService.getPostById(postId);

        return new ResponseEntity<PostDto>(PostDto, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
         this.postService.deletePost(postId);
        return  new ApiResponse("Post is deleted successfully!",true);

    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto newPostDto = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<PostDto>(newPostDto, HttpStatus.OK);

    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords) {
        List<PostDto> newPostDto = this.postService.searchPosts(keywords);

        return new ResponseEntity<List<PostDto>>(newPostDto, HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId) throws IOException {
        PostDto dto = this.postService.getPostById(postId);
        String filename = this.fileService.uploadImage(path, image);
        dto.setImage(filename);
        PostDto newDto = this.postService.updatePost(dto, postId);
        return new ResponseEntity<PostDto>(newDto, HttpStatus.OK);

    }

    // method to serve files
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName,
            HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
    //for testing purpose
    @PostMapping("/post/image/upload")
    public ResponseEntity<PostDto> uploadPostImageAsPerId(
            @RequestParam("image") MultipartFile image,
            @RequestParam("userData")String postdto) throws IOException {
    	try {
    	PostDto dto1=objectMapper.readValue(postdto, PostDto.class);
    		Integer postId= dto1.getPostId();
            PostDto dto = this.postService.getPostById(postId);
            String filename = this.fileService.uploadImage(path, image);
            dto.setImage(filename);
            PostDto newDto = this.postService.updatePost(dto, postId);
            log.info("image uploaded for post id {}",postId);
            return new ResponseEntity<PostDto>(newDto, HttpStatus.OK);
    	}catch (Exception e) {
			// TODO: handle exception
		}
        
        return null;

    }
    
    
    

}
