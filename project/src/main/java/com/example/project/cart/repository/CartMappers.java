package com.example.project.cart.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project.cart.dto.CartDto;
import com.example.project.cart.dto.CreateCart;

@Mapper
public interface CartMappers {
	void createCart(CreateCart createCart);
	List<CartDto> findCart(String uId);
}
