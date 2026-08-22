package com.Springecom.EcomProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name="category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long categoryId;
    @NotBlank
    @Size(min=5,message = "Please input atleast 5 characteres")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Product>products;
}
