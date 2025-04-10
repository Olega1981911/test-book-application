package com.book.test;

import com.book.test.data.Book;
import com.book.test.dto.BookCreateDTO;
import com.book.test.dto.BookDTO;
import com.book.test.mapper.BookMapper;
import com.book.test.repository.BookRepository;
import com.book.test.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookMapper bookMapper;

    @Test
    public void whenSavingBookSuccessfully() {
        BookCreateDTO bookDTO = BookCreateDTO.builder()
                .vendorCode("12345")
                .title("Book Title")
                .year(2025)
                .brand("Издательство")
                .stock(100)
                .price(499.99)
                .build();

        Book bookEntity = Book.builder()
                .vendorCode("12345")
                .title("Book Title")
                .year(2025)
                .brand("Издательство")
                .stock(100)
                .price(499.99)
                .build();

        BookDTO savedBookDTOEntity = BookDTO.builder()
                .vendorCode("12345")
                .title("Book Title")
                .year(2025)
                .brand("Издательство")
                .stock(100)
                .price(499.99)
                .build();

        when(bookRepository.save(any(Book.class))).thenReturn(bookEntity);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(savedBookDTOEntity);
        when(bookMapper.toEntity(any(BookCreateDTO.class))).thenReturn(bookEntity);

        BookDTO savedBookDTO = bookService.saveBook(bookDTO);

        assertNotNull(savedBookDTO);
        assertEquals("Book Title", savedBookDTO.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookMapper, times(1)).toDTO(any(Book.class));
    }

    @Test
    void testUpdateBook() {
        Long id = 1L;
        Book bookEntity = Book.builder()
                .id(id)
                .title("Test Book")
                .vendorCode("12345")
                .year(2025)
                .brand("Издательство")
                .stock(100)
                .price(499.99)
                .build();

        BookDTO bookDTO = BookDTO.builder()
                .id(id)
                .title("Test Book")
                .vendorCode("12345")
                .year(2025)
                .brand("Издательство")
                .stock(100)
                .price(499.99)
                .build();

        when(bookRepository.findById(id)).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any(Book.class))).thenReturn(bookEntity);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(bookDTO);
        when(bookMapper.toEntityFromBookDTO(any(BookDTO.class))).thenReturn(bookEntity);

        BookDTO updatedBookDTO = bookService.updateBook(bookDTO);
        assertNotNull(updatedBookDTO);
        assertEquals(id, updatedBookDTO.getId());
        assertEquals("Test Book", updatedBookDTO.getTitle());
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookMapper, times(1)).toDTO(any(Book.class));
    }

    @Test
    void testFindBookById() {
        Long id = 1L;
        Book bookEntity = Book.builder()
                .id(id)
                .title("Test Book")
                .build();
        BookDTO bookDTO = BookDTO.builder()
                .id(id)
                .title("Test Book")
                .build();

        when(bookRepository.findById(id)).thenReturn(Optional.of(bookEntity));
        when(bookMapper.toDTO(any(Book.class))).thenReturn(bookDTO);

        BookDTO foundBook = bookService.findBookById(id);
        assertNotNull(foundBook);
        assertEquals(id, foundBook.getId());
        assertEquals("Test Book", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(id);
        verify(bookMapper, times(1)).toDTO(any(Book.class));
    }

    @Test
    void testRemoveBookFailure() {
        Long id = 1L;
        when(bookRepository.existsById(id)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> bookService.removeBook(id));
        verify(bookRepository, times(1)).existsById(id);
        verify(bookRepository, never()).deleteById(id);
    }

}
