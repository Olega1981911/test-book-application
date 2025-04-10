package com.book.test;

import com.book.test.data.Book;
import com.book.test.repository.BookRepository;
import com.book.test.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void whenSavingBookSuccessfully(){
        Book book = Book.builder()
                .vendorCode("12345")
                .title("Book Title")
                .year(2025)
                .brand("Издательство")
                .stock(100)
                .price(499.99)
                .build();
        when(bookRepository.save(book)).thenReturn(book);
        Book savedBook = bookService.saveBook(book);
        assertNotNull(savedBook);
        assertEquals("Book Title", savedBook.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }
    @Test
    void testUpdateBook() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        book.setTitle("Test Book");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book updatedBook = bookService.updateBook(book);
        assertNotNull(updatedBook);
        assertEquals(id, updatedBook.getId());
        assertEquals("Test Book", updatedBook.getTitle());
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testFindBookById() {
        Long id = 1L;
        Book book = new Book();
        book.setId(id);
        book.setTitle("Test Book");
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        Book foundBook = bookService.findBookById(id);
        assertNotNull(foundBook);
        assertEquals(id, foundBook.getId());
        assertEquals("Test Book", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void testRemoveBook() {
        Long id = 1L;
        when(bookRepository.existsById(id)).thenReturn(true);
        bookService.removeBook(id);
        verify(bookRepository, times(1)).existsById(id);
        verify(bookRepository, times(1)).deleteById(id);
    }

}
