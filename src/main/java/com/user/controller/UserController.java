package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entities.User;
import com.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;
	
	//create user
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User u=service.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(u);
	}
	
	//get user
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") String id){
		User u=service.getUser(id);
		if(u==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(u);
	}
	
	//get all user
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = service.getAllUser();
		if(allUser==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(allUser);
	}
	
}
