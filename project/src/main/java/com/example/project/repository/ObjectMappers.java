package com.example.project.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project.dto.FirstEnergyDto;
import com.example.project.dto.FirstGreenDto;
import com.example.project.dto.GreenObjectDto;

@Mapper
public interface ObjectMappers {
	List<FirstGreenDto> findAllObjects();
	List<FirstEnergyDto> findAllEnergy();
	List<String> findImagesByProductId(String productId);
	GreenObjectDto findId(String productId);
}
