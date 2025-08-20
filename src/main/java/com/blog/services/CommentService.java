package com.blog.services;

import com.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto addComment(CommentDto dto, Integer postId, Integer userId);
	void deleteComment(Integer commentId);
}
