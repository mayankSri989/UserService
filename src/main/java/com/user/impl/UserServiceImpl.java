package com.user.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.entities.Hotel;
import com.user.entities.Rating;
import com.user.entities.User;
import com.user.exception.ResourceNotFoundException;
import com.user.repositories.UserRepository;
import com.user.service.UserService;
import com.user.service.external.HotelServiceImpl;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelServiceImpl hotelServiceImpl;
	
	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);
		return repository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		List<User> listUser = repository.findAll();
		List<User> listUser1 = listUser.stream().map(user->{
			
			User user2 = getUser(user.getUserId());
			return user2;
		}).collect(Collectors.toList());
		
		return listUser1;
		
	}

	@Override
	public User getUser(String userId) {
		
		User user = repository.findById(userId)
		.orElseThrow(()-> new ResourceNotFoundException("User with given id is not found"));
		Rating[] forObject = restTemplate.getForObject("http://RATING-SERVICE/rating/users/"+user.getUserId(),Rating[].class);
		List<Rating> obj = Arrays.stream(forObject).toList();
		logger.info("{}", obj);
		List<Rating> ratingList = obj.stream().map(rating-> {
			//set hotel to all the rating by call hotelserviceImpl api
			//using resttemplate
//			ResponseEntity<Hotel> entity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//			Hotel hotel = entity.getBody();
			//using feign client
			
			Hotel hotel = hotelServiceImpl.getHotel(rating.getHotelId());
			rating.setHotel(hotel);
			
//			logger.info("status code :{}",entity.getStatusCode());
			
			return rating;
		}).collect(Collectors.toList());
		
		user.setRating(ratingList);
	    return user;
	}

	@Override
	public void deleteUser(String userId) {
		repository.deleteById(userId);
		
	}

	
}
