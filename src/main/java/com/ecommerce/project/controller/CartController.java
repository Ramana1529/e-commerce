package com.ecommerce.project.controller;

import com.ecommerce.project.dto.request.AddToCartRequestDTO;
import com.ecommerce.project.dto.response.CartResponseDTO;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.mapper.CartMapper;
import com.ecommerce.project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public CartResponseDTO addToCart(@RequestBody AddToCartRequestDTO request) {
        return CartMapper.toDto(
                cartService.addToCart(
                        request.getUserId(),
                        request.getProductId(),
                        request.getQuantity()
                )
        );
    }

    @GetMapping("/{userId}")
    public CartResponseDTO getCart(@PathVariable Long userId) {
        return CartMapper.toDto(cartService.getCart(userId));
    }

    @DeleteMapping("/remove")
    public CartResponseDTO removeFromCart(@RequestParam Long userId,
                                          @RequestParam Long productId) {
        return CartMapper.toDto(cartService.removeFromCart(userId, productId));
    }

    @DeleteMapping("/clear/{userId}")
    public String clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return "Cart cleared successfully";
    }
}