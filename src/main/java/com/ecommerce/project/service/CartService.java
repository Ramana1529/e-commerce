package com.ecommerce.project.service;

import com.ecommerce.project.dto.request.AddToCartRequestDTO;
import com.ecommerce.project.dto.response.CartResponseDTO;

public interface CartService {
    CartResponseDTO addToCart(AddToCartRequestDTO request);
    CartResponseDTO getCart();
    CartResponseDTO removeFromCart(Long productId);
    void clearCart();
}
