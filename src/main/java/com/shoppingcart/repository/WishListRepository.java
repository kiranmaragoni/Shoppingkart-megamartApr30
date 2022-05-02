package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.entity.WishList;

public interface WishListRepository extends JpaRepository<WishList, Integer> {

}
