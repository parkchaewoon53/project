package com.example.project.cart.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.project.cart.dto.CartDto;
import com.example.project.cart.dto.CreateCart;
import com.example.project.cart.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class CarController {

	private final CartService cartService;

	@PostMapping("/cart")
	public void addToCart(@RequestBody CreateCart createCart) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((UserDetails) authentication.getPrincipal()).getUsername(); 
		createCart.setUId(userId);
		cartService.createCar(createCart);
	}
	@GetMapping("/cart")
	public List<CartDto> getCart() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String uId = ((UserDetails) auth.getPrincipal()).getUsername(); 
	    return cartService.getCart(uId); 
	}
}
