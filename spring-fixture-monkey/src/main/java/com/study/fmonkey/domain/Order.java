package com.study.fmonkey.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    @NotNull
    @Min(value = 1)
    Integer quantity;

    @NotNull
    @PastOrPresent
    Instant orderedAt;
}
