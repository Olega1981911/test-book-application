package com.book.test.controller;

import com.book.test.data.Book;
import com.book.test.dto.BookCreateDTO;
import com.book.test.dto.BookDTO;
import com.book.test.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookWebController {
    private final BookService bookService;

    @GetMapping
    public String showBooks(Model model,
                            @RequestParam(required = false) String title,
                            @RequestParam(required = false) String brand,
                            @RequestParam(required = false) Integer year,
                            @RequestParam Pageable pageable) {
        Page<BookDTO> books = bookService.findAllBooks(title, brand, year, pageable);
        model.addAttribute("books", books);
        return "books";

    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "addBook";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute BookCreateDTO book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        BookDTO book = bookService.findBookById(id);
        model.addAttribute("book", book);
        return "editBook";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @Valid @ModelAttribute BookDTO book, BindingResult resul) {
        if (resul.hasErrors()) {
            return "editBook";
        }
        book.setId(id);
        bookService.updateBook(book);
        return "redirect:/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.removeBook(id);
        return "redirect:/books";
    }
}
