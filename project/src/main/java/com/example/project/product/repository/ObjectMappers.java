package com.example.project.product.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project.product.dto.FirstEnergyDto;
import com.example.project.product.dto.FirstGreenDto;
import com.example.project.product.dto.GreenObjectDto;

@Mapper
public interface ObjectMappers {
	List<FirstGreenDto> findAllObjects();
	List<FirstEnergyDto> findAllEnergy();
	List<String> findImagesByProductId(String productId);
	GreenObjectDto findId(String productId);
}
