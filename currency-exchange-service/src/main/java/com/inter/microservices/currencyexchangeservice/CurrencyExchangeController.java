package com.inter.microservices.currencyexchangeservice;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to ) {
		
		ExchangeValue exchangeValue = 
				repository.findByFromAndTo(from, to);
		
	
		exchangeValue.setPort(
				Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}
	
	//retrieve all currency
	@GetMapping("/jpa/allcurrency")
	 public List<ExchangeValue> retrieveAllUser(){
	return repository.findAll();
	 }
	
	//Delete Currency
	@DeleteMapping("/jpa/allcurrency/{id}")
	public void deleteUser(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	//Create New Currency
	@PostMapping("/jpa/allcurrency")
	public ResponseEntity<Object> createUser(@Valid @RequestBody ExchangeValue user) {
	ExchangeValue savedUser = repository.save(user);
	
	URI location = ServletUriComponentsBuilder
	.fromCurrentRequest()
	.path("/{id}")
	.buildAndExpand(savedUser.getId()).toUri();
	
	return ResponseEntity.created(location).build();
	
	}
	
	
}
