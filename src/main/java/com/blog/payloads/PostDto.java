package com.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;




import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer PostId;
    @NotEmpty
    private String title;

    private String content;

    private String image;

    private Date date;
   
    private CategoryDto category;
    
    private UserDto user;
    
    private Set<CommentDto> comments= new HashSet<>();
    
    

}
