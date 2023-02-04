package com.user.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.entities.Hotel;

@FeignClient(name="HOTEL-SERVICE")
public interface HotelServiceImpl {

	@GetMapping("/hotels/{hotelId}")
	Hotel getHotel(@PathVariable("hotelId")  String hotelId);
}
