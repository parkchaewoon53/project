package com.example.project.product.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class GreenObjectDto {
	private String productId;
	private String name;
	private LocalDateTime makeDate;
	private String classification;
	private String authenticationPeriod;
	private String madeIn;
	private String kc;
	private String category;
	private String registrationNum;
	private String company;
	private int savingRate;
	private int prices;
	private int mileage;
	private List<String> images;
}
