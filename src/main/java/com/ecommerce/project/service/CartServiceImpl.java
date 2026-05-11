package com.ecommerce.project.service;

import com.ecommerce.project.dto.request.AddToCartRequestDTO;
import com.ecommerce.project.dto.response.CartResponseDTO;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.CartItem;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.exception.BadRequestException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.mapper.CartMapper;
import com.ecommerce.project.repository.CartItemRepository;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof CustomUserDetails userDetails)) {
            throw new BadRequestException("Invalid authentication");
        }

        return userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Cart getOrCreateCart(User user) {

        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setItems(new ArrayList<>());
                    cart.setTotalPrice(BigDecimal.ZERO);
                    return cartRepository.save(cart);
                });
    }
    @Override
    @Transactional
    public CartResponseDTO addToCart(AddToCartRequestDTO request) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->new ResourceNotFoundException("product not found"));
        if (!product.getIsActive()) {
            throw new BadRequestException("Product is not available");
        }

        if (product.getStockQuantity() <= 0) {
            throw new BadRequestException("Product out of stock");
        }

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);

        int newQuantity = request.getQuantity();
        if (cartItem != null) {
            newQuantity += cartItem.getQuantity();
        }
        if (newQuantity > product.getStockQuantity()) {
            throw new BadRequestException("Only " + product.getStockQuantity() + " items available");
        }

        if (cartItem == null) {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .price(product.getPrice())
                    .quantity(newQuantity)
                    .build();
        } else {
            cartItem.setQuantity(newQuantity);
        }

        cartItemRepository.save(cartItem);
        BigDecimal total = cartItemRepository.findByCartId(cart.getId())
                .stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
        cartRepository.save(cart);

        return CartMapper.toDto(cart);
    }
    @Override
    @Transactional
    public CartResponseDTO removeFromCart(Long productId) {

        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not in cart"));

        cartItemRepository.delete(cartItem);

        return CartMapper.toDto(cart);
    }
    @Override
    public CartResponseDTO getCart() {
        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setItems(new ArrayList<>());
                    newCart.setTotalPrice(BigDecimal.ZERO);
                    return cartRepository.save(newCart);
                });

        return CartMapper.toDto(cart);
    }
    @Override
    @Transactional
    public void clearCart() {

        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user);

        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
    }
}
