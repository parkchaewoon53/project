package com.example.project.address.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project.address.dto.AddressDto;
import com.example.project.address.dto.CreateAdDto;
import com.example.project.address.repository.AddressMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressSerivce {
	private final AddressMapper mapper;

	@Override
	public void createAddress(CreateAdDto createAd) {
		mapper.createAddress(createAd);
	}

	@Override
	public List<AddressDto> getAddress(String uId) {
		return mapper.findAddress(uId);
	}

	@Override
	public void deleteAddress(String uId, int no) {
		mapper.deleteAddress(uId, no);
	}
}
