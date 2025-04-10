package com.book.test.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Требуется код поставщика")
    private String vendorCode;
    @NotBlank(message = "Заголовок обязателен")
    private String title;
    private Integer year;
    @NotBlank(message = "Название обязательно")
    private String brand;
    private Integer stock;
    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(vendorCode, book.vendorCode) && Objects.equals(title, book.title) && Objects.equals(year, book.year) && Objects.equals(brand, book.brand) && Objects.equals(stock, book.stock) && Objects.equals(price, book.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorCode, title, year, brand, stock, price);
    }
}
