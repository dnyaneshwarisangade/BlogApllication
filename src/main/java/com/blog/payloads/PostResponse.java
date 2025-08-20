package com.blog.payloads;

import java.util.List;

import com.blog.payloads.PostDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> content;
    private int pageSize;
    private int pageNumber;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;

}
