package com.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;

import java.util.List;

import org.modelmapper.ModelMapper;

@SpringBootApplication

public class BlogAppApiApplication implements CommandLineRunner{
	
	@Autowired
    private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
		
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String encoded = encoder.encode("abcd123");
	    System.out.println("encoded password:"+encoded);
        return encoder;
    }
	
	@Override
	public void run(String... args) throws Exception{
		  
		  
		try {  //Role role = new Role();
//		  role.setId(AppConstants.ADMIN_USER);
//		  role.setName("ROLE_ADMIN");
//		 this.roleRepo.save(role);
//		 
//		  Role role1 = new Role();
//		  role1.setId(AppConstants.NORMAL_USER);
//		  role1.setName("ROLE_NORMAL");
//		  this.roleRepo.save(role1);
		  
//		  	List<Role> roles=List.of(role,role1);
//		  	List<Role> result=this.roleRepo.saveAll(roles);
//		  	
//		  	result.forEach(r->{
//		  		 System.out.println("name of role: "+r.getName());
//		  	});
		
		}
		  	
	    catch (Exception e) {
			 e.printStackTrace();
		}
		  
		  
	}



}
