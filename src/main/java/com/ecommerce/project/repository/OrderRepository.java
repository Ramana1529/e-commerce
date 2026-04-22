package com.ecommerce.project.repository;

import com.ecommerce.project.entity.Order;
import com.ecommerce.project.entity.OrderStatus;
import com.ecommerce.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
}
