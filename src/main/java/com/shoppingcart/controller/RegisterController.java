package com.shoppingcart.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.entity.Account;
import com.shoppingcart.repository.AccountRepository;

@RestController
@RequestMapping(value = "users")
public class RegisterController {
	// private static final Logger logger =
	// LoggerFactory.getLogger(RegisterController.class);

	
	private AccountRepository accountRepository;

	@Autowired
	public RegisterController(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@PostMapping(value = "/account")
	public ResponseEntity<?> register(@Valid @RequestBody Account account) {
		if(account.getEmail()==null||account.getEmail().isEmpty()) {
			return new ResponseEntity<>("Email Is Required",HttpStatus.BAD_REQUEST);
		}
		Optional<Account> accountFromDB = accountRepository.findByEmail(account.getEmail());
		if (!accountFromDB.isPresent()) {
			if (account.getPassword().equals(account.getConfirmPassword())) {
				if (account.getWishList() != null) {
					return new ResponseEntity<>("Only Registered Account can add wishlist", HttpStatus.BAD_REQUEST);
				} else {
					accountRepository.save(account);
					return new ResponseEntity<>("Account Registered", HttpStatus.CREATED);
				}
			}
			return new ResponseEntity<>("Password and ConfirmPassword are not same", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Email Already Exists Please Log In", HttpStatus.BAD_REQUEST);
	}


}
