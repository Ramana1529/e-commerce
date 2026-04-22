package com.ecommerce.project.service;

import com.ecommerce.project.entity.Cart;
import com.ecommerce.project.entity.CartItem;
import com.ecommerce.project.entity.Product;
import com.ecommerce.project.repository.CartItemRepository;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Cart addToCart(Long userId, Long productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found")));
                    return cartRepository.saveAndFlush(newCart);
                });

        cart = cartRepository.findById(cart.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found after save"));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElse(null);

        int totalQty = (cartItem != null)
                ? cartItem.getQuantity() + quantity
                : quantity;

        if (product.getStockQuantity() < totalQty) {
            throw new RuntimeException("Only " + product.getStockQuantity() + " items available");
        }

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setPrice(product.getPrice());
        }

        cartItem.setQuantity(totalQty);
        cartItemRepository.saveAndFlush(cartItem);

        return cart;
    }

    @Transactional
    public Cart removeFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("cart not found"));
        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Product not in cart"));

        cartItemRepository.delete(cartItem);

        return cart;
    }

    public BigDecimal calculateCartTotal(Cart cart) {
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return cart.getItems()
                .stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Cart getCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("cart not there for particular user"));
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getCart(userId);

        cartItemRepository.deleteAll(cart.getItems()); // ✅ delete from DB
        cart.getItems().clear();
    }
}
