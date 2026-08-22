package com.Springecom.EcomProject.repository;

import com.Springecom.EcomProject.model.Category;
import com.Springecom.EcomProject.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByPrice(Category category, Pageable pageable);

    List<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageable);
}
