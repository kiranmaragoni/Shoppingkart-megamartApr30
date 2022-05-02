package com.shoppingcart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcart.dao.CartDao;
import com.shoppingcart.dao.ProductDao;
import com.shoppingcart.dao.UserDao;
import com.shoppingcart.entity.Cart;
import com.shoppingcart.entity.Item;
import com.shoppingcart.entity.Product;
import com.shoppingcart.entity.User;
import com.shoppingcart.exception.EmptyListOfProductsException;
import com.shoppingcart.exception.InternalErrorException;
import com.shoppingcart.exception.InvalidQuantityException;
import com.shoppingcart.exception.NoSuchProductException;
import com.shoppingcart.exception.NoSuchUserException;
import com.shoppingcart.exception.ProductNotPresentInCartException;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.repository.UserRepository;
import com.shoppingcart.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CartDao cartDao;
	
	@Autowired
	ProductDao productDao;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(int userId) {
		return userRepository.findById(userId).get();
	}

	@Transactional(rollbackFor = InternalErrorException.class)
	@Override
	public User addUser(User user) {
		return userRepository.save(user);
	}

	@Transactional(rollbackFor = InternalErrorException.class)
	@Override
	public User updateUser(int userId, String userName) throws NoSuchUserException {
		return userDao.updateUser(userId, userName);
	}

	@Transactional(rollbackFor = InternalErrorException.class)
	@Override
	public void deleteUser(int userId) throws NoSuchUserException {
		userDao.deleteUser(userId);
	}

	@Override
	public void checkUser(int userId) throws NoSuchUserException {
		userDao.checkUser(userId);
	}

	@Override
	public List<Item> getProductsInCart(int cartId) throws InternalErrorException {
		return cartDao.getProductsInCart(cartId);
	}

	@Transactional(rollbackFor = InternalErrorException.class)
	@Override
	public Cart addProductInCart(int cartId, int productId) {
		return cartDao.addProductInCart(cartId, productId);
	}

	@Transactional(rollbackFor = InternalErrorException.class)
	@Override
	public Cart updateProductQuantityInCart(int cartId, int productId, int quantity)
			throws ProductNotPresentInCartException, InvalidQuantityException {
		return cartDao.updateProductQuantityInCart(cartId, productId, quantity);
	}

	@Transactional(rollbackFor = InternalErrorException.class)
	@Override
	public Cart removeProductFromCart(int cartId, int productId) throws ProductNotPresentInCartException {
		return cartDao.removeProductFromCart(cartId, productId);
	}

	@Transactional(rollbackFor = InternalErrorException.class)
	@Override
	public Cart removeAllProductsFromCart(int cartId) {
		return cartDao.removeAllProductsFromCart(cartId);
	}

	@Override
	public Cart getCart(int cartId) {
		return cartDao.getCart(cartId);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product searchProductByProductId(int productId) throws NoSuchProductException {
		return productDao.searchProductByProductId(productId);
	}

	@Override
	public Product searchProductByProductName(String prodName) throws NoSuchProductException {
		return productDao.searchProductByProductName(prodName);
	}

	@Override
	public List<Product> searchProductsByCategory(String category) throws EmptyListOfProductsException {
		return productDao.searchProductsByCategory(category);
	}

	@Override
	public void checkProduct(int productId) throws NoSuchProductException {
		productDao.checkProduct(productId);
	}

}
