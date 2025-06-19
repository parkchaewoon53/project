package com.example.project.product.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FirstEnergyDto {
	private String productId;
	private String name;
	private int prices;
	private int mileage;
	private LocalDate makeDate;
	private String image;
	private String category;
}
