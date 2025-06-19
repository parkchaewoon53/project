package com.example.project.product.service;

import java.util.List;

import com.example.project.product.dto.FirstEnergyDto;
import com.example.project.product.dto.FirstGreenDto;
import com.example.project.product.dto.GreenObjectDto;

public interface ObjectService {
	List<FirstGreenDto> getAllObjects();
	List<FirstEnergyDto> getAllEnergy();
	GreenObjectDto getId(String productId);
}
