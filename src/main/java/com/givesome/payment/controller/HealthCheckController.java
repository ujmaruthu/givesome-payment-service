package com.givesome.payment.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2(topic = "Health Check Controller")

@RestController
@CrossOrigin(origins = "${app.api.cors_origin}")
public class HealthCheckController {

	@GetMapping("/health")
	public ResponseEntity<Object> getCall() {
		log.info("Health Check Call");
		return ResponseEntity.status(HttpStatus.OK).body("Health Check - Success");
	}

}
