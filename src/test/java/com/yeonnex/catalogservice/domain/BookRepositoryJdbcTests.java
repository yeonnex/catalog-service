package com.yeonnex.catalogservice.domain;

import com.yeonnex.catalogservice.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest // 스프링 데이터 JDBC 컴포넌트를 집중적으로 테스트하는 클래스임을 나타냄, 각 테스트 메서드르 ㄹ트랜잭션으로 실행하고 실행이 끝날때마다 롤백을 수행해 데이터베이스를 원상태로 되돌린다.
@Import(DataConfig.class) // 감사를 활성화하는 데이터 설정 임포트
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE // 테스트컨테이너를 이용해야하기 때문에 내장 테스트 데이터베이스 사용을 비활성화
)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    JdbcAggregateTemplate jdbcAggregateTemplate; // 데이터베이스와 상호작용하기 위한 하위수준의 객체

    @Test
    void findBookByIsbnWhenExisting() {
        var bookIsbn = "1234561237";
        var book = Book.of(bookIsbn, "Title", "Author", 12.90, "ming");
        jdbcAggregateTemplate.insert(book);
        Optional<Book> actualBook = bookRepository.findByIsbn(bookIsbn);

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().isbn()).isEqualTo(bookIsbn);
    }
}
