package com.Springecom.EcomProject.service;

import com.Springecom.EcomProject.payload.CartDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface CartService {
    CartDTO addProduct(Long productId, Integer quantity) ;

    List<CartDTO> getCart();
    CartDTO getCart(String emailId, Long cartId);
    @Transactional
    CartDTO updateProductQuantityInCart(Long productId, Integer quantity);

    String deleteProductFromCart(Long cartId, Long productId);

    void updateProductInCarts(Long cartId, Long productId);
}
