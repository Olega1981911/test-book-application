package com.book.test.service;

import com.book.test.data.Book;
import com.book.test.dto.BookCreateDTO;
import com.book.test.dto.BookDTO;
import com.book.test.exeptions.BookNotFoundException;
import com.book.test.mapper.BookMapper;
import com.book.test.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Page<BookDTO> findAllBooks(String title, String brand, Integer year, Pageable pageable) {
        if (title == null || title.isEmpty()) {
            throw new RuntimeException("Заголовок не может быть пустым");
        }
        if (brand == null || brand.isEmpty()) {
            brand = null;
        }
        Page<Book> books = bookRepository.findAllByTitleContainingAndBrandContainingAndYear(title, brand, year, pageable);
        return books.map(bookMapper::toDTO);
    }

    public BookDTO findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        return bookMapper.toDTO(book);
    }

    public BookDTO findBookByTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new RuntimeException("Заголовок не может быть пустым");
        }
        Book book = bookRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Книга не найдена"));
        return bookMapper.toDTO(book);
    }

    public BookDTO saveBook(BookCreateDTO createDTO) {
        Book book = bookMapper.toEntity(createDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDTO(savedBook);
    }


    public BookDTO updateBook(BookDTO bookDTO) {
        Book book = bookMapper.toEntityFromBookDTO(bookDTO);
        Book oldBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> new RuntimeException("Книга не найдена по id: " + book.getId()));
        oldBook.setTitle(book.getTitle());
        oldBook.setVendorCode(book.getVendorCode());
        oldBook.setYear(book.getYear());
        oldBook.setBrand(book.getBrand());
        oldBook.setStock(book.getStock());
        oldBook.setPrice(book.getPrice());
        Book savedBook = bookRepository.save(oldBook);
        return bookMapper.toDTO(savedBook);

    }

    public void removeBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Книга не найдена по id: " + id);
        }
        bookRepository.deleteById(id);
    }

}
