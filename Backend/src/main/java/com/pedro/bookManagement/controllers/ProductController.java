package com.pedro.bookManagement.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class ProductController {

	@GetMapping("/{id}")
		public String hello(@PathVariable final String id) {
			return "Hello World " + id;
		}
		
	
}
