package com.example.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project.dto.FirstEnergyDto;
import com.example.project.dto.FirstGreenDto;
import com.example.project.dto.GreenObjectDto;
import com.example.project.repository.ObjectMappers;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceImpl implements ObjectService {

	private final ObjectMappers mapper;

	@Override
	public List<FirstGreenDto> getAllObjects() {
		return mapper.findAllObjects();
	}

	@Override
	public List<FirstEnergyDto> getAllEnergy() {
		return mapper.findAllEnergy();
	}

	@Override
	public GreenObjectDto getId(String productId) {
		GreenObjectDto product = mapper.findId(productId);
		List<String> images = mapper.findImagesByProductId(productId);
		product.setImages(images);
		return product;
	}

}
