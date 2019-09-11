package com.example.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Blah {

	@GetMapping("/ah")
	public String get() {
		return "Hi!";
	}
}
