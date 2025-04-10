package com.book.test.controller;

import com.book.test.dto.BookCreateDTO;
import com.book.test.dto.BookDTO;
import com.book.test.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @GetMapping
    public ResponseEntity<Page<BookDTO>> findAll(@RequestParam(required = false) String title,
                                                 @RequestParam(required = false) String brand,
                                                 @RequestParam(required = false) Integer year,
                                                 @RequestParam Pageable pageable
                                              ) {

        return ResponseEntity.ok(bookService.findAllBooks(title, brand, year, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<BookDTO> findBookByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.findBookByTitle(title));
    }


    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookCreateDTO book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        bookDTO.setId(id);
        return ResponseEntity.ok(bookService.updateBook(bookDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.removeBook(id);
        return ResponseEntity.noContent().build();
    }
}
