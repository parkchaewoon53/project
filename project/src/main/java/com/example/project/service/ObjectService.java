package com.example.project.service;

import java.util.List;

import com.example.project.dto.FirstEnergyDto;
import com.example.project.dto.FirstGreenDto;
import com.example.project.dto.GreenObjectDto;

public interface ObjectService {
	List<FirstGreenDto> getAllObjects();
	List<FirstEnergyDto> getAllEnergy();
	GreenObjectDto getId(String productId);
}
