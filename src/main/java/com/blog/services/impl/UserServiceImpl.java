package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;
import com.blog.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService{
	 @Autowired
	    private UserRepo userRepo;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Autowired
	    private RoleRepo roleRepo;

	    @Override
	    public UserDto createUser(UserDto user) {
	        User user_model = this.modelMapper.map(user, User.class);
	        User savedUser = this.userRepo.save(user_model);
	        return this.modelMapper.map(savedUser, UserDto.class);
	    }

	    @Override
	    public UserDto updateUser(UserDto user, Integer Id) {
	        User user_model = this.modelMapper.map(user, User.class);
	        this.userRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("User", "id", Id));
	        user_model.setName(user.getName());
	        user_model.setEmail(user.getEmail());
	        user_model.setPassword(user.getPassword());
	        user_model.setAbout(user.getAbout());
	        User saved_user = this.userRepo.save(user_model);
	        return this.modelMapper.map(saved_user, UserDto.class);

	    }

	    @Override
	    public UserDto getUserById(Integer Id) {
	        User user = this.userRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("User", "id", Id));
	        return this.modelMapper.map(user, UserDto.class);
	    }

	    @Override
	    public List<UserDto> getAllUsers() {
	        List<User> users = this.userRepo.findAll();
	        List<UserDto> dto_list = users.stream().map(u -> this.modelMapper.map(u, UserDto.class))
	                .collect(Collectors.toList());
	        return dto_list;
	    }

	    @Override
	    public void deleteUser(Integer Id) {
	        User user = this.userRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("User", "id", Id));
	        this.userRepo.delete(user);
}

		@Override
		public UserDto registerNewUser(UserDto userdto) {
			User user = this.modelMapper.map(userdto, User.class);
			//set encoded password
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			
			//role
			Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
			user.getRoles().add(role);
			User newUser =this.userRepo.save(user);
			return this.modelMapper.map(user, UserDto.class);
		}
}
