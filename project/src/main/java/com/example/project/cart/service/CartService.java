package com.example.project.cart.service;

import java.util.List;

import com.example.project.cart.dto.CartDto;
import com.example.project.cart.dto.CreateCart;

public interface CartService {
	void createCar (CreateCart createCart);
	List <CartDto> getCart(String uId);
}
