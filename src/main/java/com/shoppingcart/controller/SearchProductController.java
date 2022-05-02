package com.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.entity.Product;
import com.shoppingcart.repository.ProductRepository;

@RestController
@RequestMapping(value = "search")
public class SearchProductController {

	@Autowired
	private ProductRepository productRepository;
	

	@GetMapping(value = "/productByName/{prodName}")
	public ResponseEntity<?> getProductByName(@PathVariable String prodName) {
		try {
			Product product = productRepository.findByProdName(prodName);

			if (product == null) {
				return new ResponseEntity<String>("No Product is found with product name :" + prodName, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Product>(productRepository.findByProdName(prodName), HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/productById/{prodId}")
	public ResponseEntity<?> getProductById(@PathVariable Integer prodId) {
		try {

			Product product = productRepository.findByproductId(prodId);

			if (product == null) {
				return new ResponseEntity<String>("No Product is found with product with ID :" + prodId, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(productRepository.findById(prodId), HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

}
