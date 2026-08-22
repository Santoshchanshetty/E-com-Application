package com.Springecom.EcomProject.controller;
import com.Springecom.EcomProject.config.Appconstants;
import com.Springecom.EcomProject.payload.ProductDTO;
import com.Springecom.EcomProject.payload.ProductResponse;
import com.Springecom.EcomProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")

public class ProductController {
    @Autowired
    public ProductService productService;
    @PostMapping("admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO, @PathVariable Long categoryId){
        ProductDTO addingProduct=productService.addProduct(productDTO,categoryId);
                return new ResponseEntity<>(addingProduct, HttpStatus.CREATED);
    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(@RequestParam(name="pageNumber",defaultValue = Appconstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                          @RequestParam(name="pageSize",defaultValue = Appconstants.PAGE_SIZE,required = false)Integer pageSize,
                                                          @RequestParam(name="sortBy",defaultValue = Appconstants.SORT_PRODUCT_BY,required = false)String sortBy,
                                                          @RequestParam(name="sortOrder",defaultValue = Appconstants.SORT_BY,required = false)String sortOrder){
        ProductResponse productResponse=productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
                                                                 @RequestParam(name="pageNumber",defaultValue = Appconstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                                 @RequestParam(name="pageSize",defaultValue = Appconstants.PAGE_SIZE,required = false)Integer pageSize,
                                                                 @RequestParam(name="sortBy",defaultValue = Appconstants.SORT_PRODUCT_BY,required = false)String sortBy,
                                                                 @RequestParam(name="sortOrder",defaultValue = Appconstants.SORT_BY,required = false)String sortOrder){
        ProductResponse productResponse=productService.getProductsByCategory(pageNumber,pageSize,sortBy,sortOrder,categoryId);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    @GetMapping("/public/categories/products/{keyword}")
    public ResponseEntity<ProductResponse>getProductByKeyword(@PathVariable String keyword,@RequestParam(name="pageNumber",defaultValue = Appconstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                              @RequestParam(name="pageSize",defaultValue = Appconstants.PAGE_SIZE,required = false)Integer pageSize,
                                                              @RequestParam(name="sortBy",defaultValue = Appconstants.SORT_PRODUCT_BY,required = false)String sortBy,
                                                              @RequestParam(name="sortOrder",defaultValue = Appconstants.SORT_BY,required = false)String sortOrder){
        ProductResponse productResponse=productService.getProductByKeyword(pageNumber,pageSize,sortBy,sortOrder,keyword);
        return new ResponseEntity<>(productResponse,HttpStatus.FOUND);
    }
    @PutMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> UpdateProduct(@RequestBody ProductDTO productDTO,@PathVariable Long productId){
        ProductDTO updateProduct=productService.UpdateProduct(productDTO,productId);
        return new ResponseEntity<>(updateProduct,HttpStatus.OK);
    }
    @DeleteMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> DeleteProduct(@PathVariable Long productId){
        ProductDTO productDTO=productService.DeleteProduct(productId);
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
    @PutMapping("products/{productId}/image")
    public ResponseEntity<ProductDTO> UpdateProductImage(@PathVariable Long productId, @RequestParam("image")MultipartFile image) throws IOException {
        ProductDTO productDTO=productService.UpdateProductImage(productId,image );
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }


}
