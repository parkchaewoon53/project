package com.example.project.address.service;

import java.util.List;

import com.example.project.address.dto.AddressDto;
import com.example.project.address.dto.CreateAdDto;

public interface AddressSerivce {
	void createAddress(CreateAdDto createAd);
	List<AddressDto> getAddress(String uId);
	void deleteAddress(String uId, int no);
}
