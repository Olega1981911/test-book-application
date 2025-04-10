package com.book.test.mapper;

import com.book.test.data.Book;
import com.book.test.dto.BookCreateDTO;
import com.book.test.dto.BookDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    public BookDTO toDTO(Book book){
        return BookDTO.builder()
                .id(book.getId())
                .vendorCode(book.getVendorCode())
                .title(book.getTitle())
                .year(book.getYear())
                .brand(book.getBrand())
                .stock(book.getStock())
                .price(book.getPrice())
                .build();
    }

    public Book toEntity(BookCreateDTO bookCreateDTO){
        return Book.builder()
                .vendorCode(bookCreateDTO.getVendorCode())
                .title(bookCreateDTO.getTitle())
                .year(bookCreateDTO.getYear())
                .brand(bookCreateDTO.getBrand())
                .stock(bookCreateDTO.getStock())
                .price(bookCreateDTO.getPrice())
                .build();
    }

    public Book toEntityFromBookDTO(BookDTO bookDTO){
        return Book.builder()
                .id(bookDTO.getId())
                .vendorCode(bookDTO.getVendorCode())
                .title(bookDTO.getTitle())
                .year(bookDTO.getYear())
                .brand(bookDTO.getBrand())
                .stock(bookDTO.getStock())
                .price(bookDTO.getPrice())
                .build();
    }

    public List<BookDTO> toBookDTOs(List<Book> books){
        return books.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Book> toBooksEntity(List<BookCreateDTO> bookDTOs){
        return bookDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
