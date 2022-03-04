package mk.ukim.finki.wpaud.dto;

import lombok.Data;
import mk.ukim.finki.wpaud.model.Category;
import mk.ukim.finki.wpaud.model.Manufacturer;

import javax.persistence.ManyToOne;

@Data
public class ProductDto {


    private String name;

    private Double price;

    private Integer quantity;


    private Long category;

    private Long manufacturer;

    public ProductDto() {
    }

    public ProductDto(String name, Double price, Integer quantity, Long category, Long manufacturer) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.manufacturer = manufacturer;
    }
}
