package com.yeonnex.catalogservice.demo;

import com.yeonnex.catalogservice.domain.Book;
import com.yeonnex.catalogservice.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "polar.testdata.enabled", havingValue = "true")
@RequiredArgsConstructor
public class BookDataLoader {
    private final BookRepository bookRepository;

    @EventListener(ApplicationReadyEvent.class)
    void loadBookTestData() {
        Book book1 = new Book("1234567890", "Northern Lights", "Lyar Silverstar", 9.90);
        Book book2 = new Book("1234567891", "Polar Journey", "Seoyeon Jang", 13.80);
        bookRepository.save(book1);
        bookRepository.save(book2);
        log.info("successfully saved!");
    }
}
