package com.yeonnex.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.*;

import java.time.Instant;

public record Book(

        @Id
        Long id,

        @NotBlank(message = "The book ISBN must be defined.")
        @Pattern(
                regexp = "^([0-9]{10}|[0-9]{13})$",
                message = "The ISBN format must be valid."
        )
        String isbn,

        @NotBlank(message = "The book title must be defined.")
        String title,

        @NotBlank(message = "The book author must be defined.")
        String author,

        @NotNull(message = "The boot price must be defined.")
        @Positive(message = "The book price must be greater than zero.")
        Double price,

        String publisher, // 새로운 선택적 필드

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate,

        @Version
        int version // 낙관적 잠금을 위해 사용되는 엔티티 버전 번호


) {
        /**
         * id 필드가 null 이고 version 필드가 0 이면 Spring Data JDBC 는 새로운 객체라고 가정한다.
         * 결과적으로 테이블에 새 행을 삽입할 때 식별자 생성은 데이터베이스에 의존한다.
         * 값이 제공되면 이미 데이터베이스에서 객체를 찾아 업데이트할 것으로 기대된다.
         */
        public static Book of(String isbn, String title, String author, Double price, String publisher) {
                return new Book(null, isbn, title, author, price, publisher,null,null, 0); // ID 가 null 이고 버전이 0 이면 새로운 엔티티로 인식
        }
}