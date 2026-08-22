package com.Springecom.EcomProject.service;

import com.Springecom.EcomProject.payload.ProductDTO;
import com.Springecom.EcomProject.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(ProductDTO product, Long categoryId);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, Long categoryId);

    ProductResponse getProductByKeyword(Integer pageNumber, Integer pageSize, String keyword, String sortOrder, String s);

    ProductDTO UpdateProduct(ProductDTO product, Long productId);

    ProductDTO DeleteProduct(Long productId);

    ProductDTO UpdateProductImage(Long productId, MultipartFile image) throws IOException;
}
