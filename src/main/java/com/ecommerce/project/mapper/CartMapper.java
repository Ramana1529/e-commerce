package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.response.CartItemResponseDTO;
import com.ecommerce.project.dto.response.CartResponseDTO;
import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {
    public static CartResponseDTO toDto(Cart cart) {
        if(cart == null)return null;
        List<CartItemResponseDTO> items = cart.getItems()
                .stream()
                .map(CartMapper::mapItem)
                .toList();
        return CartResponseDTO.builder()
                .id(cart.getId())
                .items(items)
                .totalAmount(totalAmount(cart))
                .totalItems(items.size())
                .build();
    }
    public static CartItemResponseDTO mapItem(CartItem cartItem){
        if(cartItem == null)return null;
        BigDecimal totalPrice = cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        return CartItemResponseDTO.builder()
                .productId(cartItem.getProduct()!=null ? cartItem.getProduct().getId() : null)
                .productName(cartItem.getProduct()!=null?cartItem.getProduct().getName():null)
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .totalPrice(totalPrice)
                .build();
    }
    public static BigDecimal totalAmount(Cart cart){
        if(cart.getItems()==null)return BigDecimal.ZERO;
        return cart.getItems().stream()
                .map(item->item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

}
