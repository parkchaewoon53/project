package com.example.project.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateCart {
	private String uId;

    @JsonProperty("pId")
	private String pId;
	private int quantity;
	private String checkBuy;
}
