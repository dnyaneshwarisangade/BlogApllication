package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
    @NotEmpty
    @Size(min = 4, message = "user name must be of 4 characters!")
    private String name;
    @Email(message = "email address is not valid!")
    private String email;
    @NotNull
    @Size(min = 4, max = 8, message = "password must be of min 4 and max 8")
    private String password;
    @NotNull
    private String about;

    private Set<CommentDto> comments= new HashSet<>(); 
    
    private Set<RoleDto> roles = new HashSet<>();
}
