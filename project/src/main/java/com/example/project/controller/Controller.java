package com.example.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.FirstEnergyDto;
import com.example.project.dto.FirstGreenDto;
import com.example.project.dto.GreenObjectDto;
import com.example.project.service.ObjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class Controller {

	private final ObjectService objectService;

	@GetMapping("/green-object-list")
	public List<FirstGreenDto> getGreenObjects() {
		return objectService.getAllObjects();
	}

	@GetMapping("/FirstEnergy")
	public List<FirstEnergyDto> getFirstEnergy() {
		return objectService.getAllEnergy();
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<GreenObjectDto> getProductById(@PathVariable("productId") String productId) {
		GreenObjectDto product = objectService.getId(productId);
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}
}
