package com.study.fmonkey.domain.service;

import com.study.fmonkey.domain.Book;
import com.study.fmonkey.domain.Order;
import com.study.fmonkey.domain.User;
import com.study.fmonkey.repository.BookRepository;
import com.study.fmonkey.repository.OrderRepository;
import com.study.fmonkey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;

    public Order createOrder(Long userId, Long bookId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found."));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found."));

        Order order = Order.builder()
                .user(user)
                .book(book)
                .quantity(quantity)
                .orderedAt(Instant.now())
                .build();

        return orderRepository.save(order);
    }
}
