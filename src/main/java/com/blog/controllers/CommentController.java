package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	@Autowired
	CommentService commentService;
	
	@PostMapping("/post/{postId}/user/{userId}/comments")
	public ResponseEntity<CommentDto> createCommment(@RequestBody CommentDto comment, @PathVariable Integer postId, @PathVariable Integer userId){
		
		CommentDto dto=this.commentService.addComment(comment, postId,userId);
		return  new ResponseEntity<CommentDto>(dto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> createCommment(@PathVariable Integer postId){
		
		this.commentService.deleteComment(postId);
		return  new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully!", true),HttpStatus.OK);
	}

}
