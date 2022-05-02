package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppingcart.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
