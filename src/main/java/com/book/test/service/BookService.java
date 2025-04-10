package com.book.test.service;

import com.book.test.data.Book;
import com.book.test.exeptions.BookNotFoundException;
import com.book.test.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Page<Book> findAllBooks(String title, String brand, Integer year, Pageable pageable) {
        if (title == null || title.isEmpty()) {
            throw new RuntimeException("Заголовок не может быть пустым");
        }
        if (brand == null || brand.isEmpty()) {
            brand = null;
        }
        return bookRepository.findAllByTitleContainingAndBrandContainingAndYear(title, brand, year, pageable);
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book findBookByTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new RuntimeException("Заголовок не может быть пустым");
        }
        return bookRepository.findByTitle(title).orElseThrow(() -> new RuntimeException("Книга не найдена"));
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }


    public Book updateBook(Book book) {
        Book oldBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> new RuntimeException("Книга не найдена по id: " + book.getId()));
        oldBook.setTitle(book.getTitle());
        oldBook.setVendorCode(book.getVendorCode());
        oldBook.setYear(book.getYear());
        oldBook.setBrand(book.getBrand());
        oldBook.setStock(book.getStock());
        oldBook.setPrice(book.getPrice());
        return bookRepository.save(oldBook);

    }

    public void removeBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Книга не найдена по id: " + id);
        }
        bookRepository.deleteById(id);
    }

}
