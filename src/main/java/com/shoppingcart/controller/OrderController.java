package com.shoppingcart.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.entity.Account;
import com.shoppingcart.entity.Order;
import com.shoppingcart.repository.AccountRepository;
import com.shoppingcart.repository.OrderRepository;

@RestController
@RequestMapping(value = "users")
public class OrderController {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private OrderRepository orderRepository;

	@PostMapping(value = "/account/addOrder/{accountId}")
	public ResponseEntity<?> addOrder(@PathVariable("accountId") Integer accountId,
			@RequestBody @Valid Order orderInReq) {

		Optional<Account> account = accountRepository.findById(accountId);
		List<Order> orderList = new ArrayList<>();
		if (account.isPresent()) {
			Account accountObjInDB = account.get();

			//List<Order> orderList = accountObjInDB.getOrdersList();
			
			orderList.add(orderInReq);
			accountObjInDB.setOrdersList(orderList);
			accountRepository.save(accountObjInDB);
			return new ResponseEntity<>("Order Added Successfully", HttpStatus.CREATED);

		}
		return new ResponseEntity<>("Account Not Found With Id " + accountId, HttpStatus.NOT_FOUND);

	}

	@GetMapping(value = "/account/filterOrdersByStatus/{accountId}/{status}")
	public ResponseEntity<?> filterOrdersByStatus(@PathVariable("accountId") Integer accountId,
			@PathVariable("status") String status) {

		Optional<Account> account = accountRepository.findById(accountId);

		if (account.isPresent()) {
			Account accountObjInDB = account.get();
			List<Order> orderList = accountObjInDB.getOrdersList();
			List<Order> filteredOrders = new ArrayList<>();

			if (null != orderList) {
				orderList.stream().filter(o -> status.equals(o.getOrderStatus())).forEach(o -> {
					filteredOrders.add(o);
				});
			}
			if (filteredOrders.isEmpty()) {
				return new ResponseEntity<>("No Order found with Status--->" + status, HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<List<Order>>(filteredOrders, HttpStatus.FOUND);
			}
		}
		return new ResponseEntity<>("No account found with accountId--->" + accountId, HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/account/returnOrder/{accountId}/{orderId}")
	public ResponseEntity<?> returnOrder(@PathVariable("accountId") Integer accountId,
			@PathVariable("orderId") Integer orderId) {

		Optional<Order> orderInDB = orderRepository.findById(orderId);
		if (orderInDB.isPresent()) {
            Order order = orderInDB.get();
			Date orderDate = order.getOrderedDate();
			Date currentDate = new Date();
			long difference_In_Time = orderDate.getTime() - currentDate.getTime();

			long differenceInDays = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

			if (differenceInDays > 10) {
				return new ResponseEntity<>(
						"The order can't be returned as it is ordered before ten days--->",
						HttpStatus.NOT_ACCEPTABLE);
			}

			else {
				return new ResponseEntity<>(
						"Your Return Order is taken!! Your money will be Credited to your Account !!",
						HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<>("Order Not Found With Id "+orderId,HttpStatus.NOT_FOUND);

	}
}
