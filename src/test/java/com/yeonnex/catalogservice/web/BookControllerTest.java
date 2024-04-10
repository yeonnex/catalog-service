package com.yeonnex.catalogservice.web;

import com.yeonnex.catalogservice.domain.BookNotFoundException;
import com.yeonnex.catalogservice.domain.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * 스프링 MVC 컴포넌트에 중점을 두고, 명시적으로는 BookController 클래스를 타깃으로 하는 테스트 클래스 임을 나타낸다.
 * MockMvc 는 톰캣과 같은 서버롤 로드하지 않고 웹 엔드포인트를 테스트할 수 있는 유틸리티 클래스이다.
 * CatalogServiceApplicationTests 처럼 임베디드 서버를 사용하지 않기 때문에 가벼운 테스트가 가능하다.
 */
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("존재하지 않는 책을 조회했을 떄 응답코드 404 반환")
    void 존재하지_않는_책조회_404반환() throws Exception {
        String isbn = "3693693690";

        // 모의 빈이 어떻게 작동할 것인지 규정한다
        given(bookService.viewBookDetails(isbn))
                .willThrow(BookNotFoundException.class);

        mockMvc.perform(get("/books/" + isbn))
                .andExpect(status().isNotFound());
    }
}