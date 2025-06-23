package com.example.project.address.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.address.dto.AddressDto;
import com.example.project.address.dto.CreateAdDto;
import com.example.project.address.service.AddressSerivce;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class AddressController {
	private final AddressSerivce service;

	@PostMapping("/address")
	public void saveAddress(@RequestBody CreateAdDto createAdDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((UserDetails) authentication.getPrincipal()).getUsername();

		createAdDto.setUId(userId); 

		service.createAddress(createAdDto);
	}

	@GetMapping("/address")
	public List<AddressDto> getAddress() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String uId = ((UserDetails) auth.getPrincipal()).getUsername();
		return service.getAddress(uId);
	}

	@PostMapping("/deleteAddress")
	public ResponseEntity<Void> deleteUser(@RequestBody AddressDto dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uId = ((UserDetails) authentication.getPrincipal()).getUsername();
		int no = dto.getNo();
		service.deleteAddress(uId, no);
		return ResponseEntity.ok().build();
	}

}
