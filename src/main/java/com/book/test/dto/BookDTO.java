package com.book.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String vendorCode;
    private String title;
    private Integer year;
    private String brand;
    private Integer stock;
    private Double price;
}
