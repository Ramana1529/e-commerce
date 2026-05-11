package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.response.CartItemResponseDTO;
import com.ecommerce.project.dto.response.CartResponseDTO;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {

    public static CartResponseDTO toDto(Cart cart) {

        if (cart == null) return null;

        List<CartItem> cartItems =
                cart.getItems() == null ? new ArrayList<>() : cart.getItems();

        List<CartItemResponseDTO> items = cartItems.stream()
                .map(CartMapper::mapItem)
                .toList();

        BigDecimal total = totalAmount(cartItems);

        return CartResponseDTO.builder()
                .id(cart.getId())
                .items(items)
                .totalAmount(total)
                .totalItems(items.stream().mapToInt(CartItemResponseDTO::getQuantity).sum())
                .build();
    }

    public static CartItemResponseDTO mapItem(CartItem cartItem) {
        if (cartItem == null) return null;

        BigDecimal totalPrice =
                cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        return CartItemResponseDTO.builder()
                .productId(cartItem.getProduct() != null ? cartItem.getProduct().getId() : null)
                .productName(cartItem.getProduct() != null ? cartItem.getProduct().getName() : null)
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .totalPrice(totalPrice)
                .build();
    }

    private static BigDecimal totalAmount(List<CartItem> items) {
        return items.stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}