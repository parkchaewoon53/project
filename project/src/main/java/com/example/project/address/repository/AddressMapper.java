package com.example.project.address.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.project.address.dto.AddressDto;
import com.example.project.address.dto.CreateAdDto;

@Mapper
public interface AddressMapper {
	void createAddress(CreateAdDto createAddress);
	List<AddressDto> findAddress(String uId);
	void deleteAddress(@Param("uId") String uId, @Param("no") int no);
}
