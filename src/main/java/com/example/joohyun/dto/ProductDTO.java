package com.example.joohyun.dto;

import com.example.joohyun.entity.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    private long pid;
    private String pname;
    private String author;
    private int price;
    private String brand;
    private String category1;
    private String category2;

    public Product toEntity(){
        return new Product(pid, pname, author, price, brand, category1, category2);
    }
}
