package com.study.fmonkey.repository;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import com.study.fmonkey.domain.Book;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.within;


@DataJpaTest
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test()
    void 일반_테스트() {
        Book book = Book.builder()
                .id(1L)
                .name("테스트용책")
                .price(10000)
                .createdAt(Instant.now().minus(1, ChronoUnit.DAYS))
                .build();
        bookRepository.save(book);

        Book savedBook = bookRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        assertThat(savedBook).usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(book);
        assertThat(savedBook.getCreatedAt()).isCloseTo(book.getCreatedAt(), within(3, ChronoUnit.SECONDS));
    }

    @Test()
    void 일반_실패_테스트() {
        Book book = Book.builder()
                .id(1L)
                .name("테스트용책")
                .price(1) // exception
                .createdAt(Instant.now().minus(1, ChronoUnit.DAYS))
                .build();

        assertThatThrownBy(() -> bookRepository.save(book))
                .isInstanceOf(ConstraintViolationException.class);
    }


    @RepeatedTest(5)
    void Fixture_Monkey_테스트() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
        Book book = fixtureMonkey.giveMeOne(Book.class);

        Book savedBook = bookRepository.save(book);
        System.out.println(savedBook);

        assertThat(Validation.buildDefaultValidatorFactory()
                .getValidator()
                .validate(savedBook))
                .isEmpty();
    }
}