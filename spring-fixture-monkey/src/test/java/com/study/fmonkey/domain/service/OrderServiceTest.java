package com.study.fmonkey.domain.service;

import com.study.fmonkey.domain.Book;
import com.study.fmonkey.domain.Order;
import com.study.fmonkey.domain.User;
import com.study.fmonkey.repository.BookRepository;
import com.study.fmonkey.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateOrder() {
        // Given
        User user = User.builder()
                .name("Test User")
                .address("Test Address")
                .email("test@email.com")
                .build();
        userRepository.save(user);

        Book book = Book.builder()
                .name("Test Book")
                .price(10000)
                .createdAt(Instant.now())
                .build();
        bookRepository.save(book);

        // When
        Order order = orderService.createOrder(user.getId(), book.getId(), 1);

        // Then
        assertThat(order).isNotNull();
        assertThat(order.getUser().getId()).isEqualTo(user.getId());
        assertThat(order.getBook().getId()).isEqualTo(book.getId());
        assertThat(order.getQuantity()).isEqualTo(1);
    }

}