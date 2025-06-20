package com.example.project.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project.cart.dto.CartDto;
import com.example.project.cart.dto.CreateCart;
import com.example.project.cart.repository.CartMappers;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{
	private final CartMappers mappers;
	
	@Override
	public void createCar(CreateCart createCart) {
		mappers.createCart(createCart);
	}


	@Override
	public List<CartDto> getCart(String uId) {
		return mappers.findCart(uId);
	}
	
}
