package com.yeonnex.catalogservice.web;

import com.yeonnex.catalogservice.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest // JSON 직렬화에 중점을 둔 테스트 클래스임을 나타낸다
@ExtendWith(SpringExtension.class)
public class BookJsonTests {
    @Autowired
    private JacksonTester<Book> json; // JSON 직렬화 및 역직렬화를 확인하기 위한 유틸리티 클래스

    @Test
    @DisplayName("직렬화 테스트")
    void testSerialize() throws IOException {
        var book = Book.of("1234567890", "title", "author", 5.55);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
    }

    @Test
    @DisplayName("역직렬화 테스트")
    void testDeserialize() throws IOException {
        var content = """
                {
                    "isbn": "1234567890",
                    "title": "my title",
                    "author": "my author",
                    "price": 9.99
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(Book.of("1234567890", "my title", "my author", 9.99));
    }
}
