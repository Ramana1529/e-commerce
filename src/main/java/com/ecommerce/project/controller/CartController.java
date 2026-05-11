package com.ecommerce.project.controller;

import com.ecommerce.project.dto.request.AddToCartRequestDTO;
import com.ecommerce.project.dto.response.CartResponseDTO;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.mapper.CartMapper;
import com.ecommerce.project.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public CartResponseDTO addToCart(@Valid @RequestBody AddToCartRequestDTO request) {
        return cartService.addToCart(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public CartResponseDTO getCart() {

        return cartService.getCart();
    }

    @DeleteMapping("/items/{productId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public CartResponseDTO removeFromCart(@PathVariable Long productId) {
        return cartService.removeFromCart(productId);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public String clearCart() {
        cartService.clearCart();
        return "Cart cleared successfully";
    }
}