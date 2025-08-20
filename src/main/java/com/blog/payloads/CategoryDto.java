package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Integer CategoryId;
    @NotEmpty
    @Size(min = 4, message = "category title must be of minimum 4 characters!")
    private String CategoryTitle;
    @NotBlank
    @Size(max = 80)
    private String CategoryDescription;
}
