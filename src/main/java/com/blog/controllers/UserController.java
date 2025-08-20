package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
    public ResponseEntity<UserDto> CreateUser(@Valid @RequestBody UserDto user) {
        UserDto new_user = this.userService.createUser(user);

        return new ResponseEntity<>(new_user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDto user) {

        UserDto user_dto = this.userService.updateUser(user, id);

        return ResponseEntity.ok(user_dto);
    }

    //Accessed by ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        this.userService.deleteUser(id);
        return new ResponseEntity(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer id) {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }
}
