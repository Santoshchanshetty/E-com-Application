package com.Springecom.EcomProject.controller;

import com.Springecom.EcomProject.config.Appconstants;
import com.Springecom.EcomProject.model.Category;
import com.Springecom.EcomProject.payload.CategoryDTO;
import com.Springecom.EcomProject.payload.CategoryResponse;
import com.Springecom.EcomProject.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam (name="pageNumber",defaultValue = Appconstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                             @RequestParam (name="pageSize",defaultValue = Appconstants.PAGE_SIZE,required = false)Integer pageSize,
                                                             @RequestParam(name="sortBy",defaultValue = Appconstants.SORT_BY,required = false)String sortBy,
                                                             @RequestParam(name="sortOrder",defaultValue = Appconstants.SORT_ORDER,required = false)String sortOrder){
        CategoryResponse categoryResponse=categoryService.getAllCategories(pageNumber, pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO=categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO,HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok("Product deleted successfully");

    }
    @PutMapping("/admin/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,@PathVariable Long categoryId){
           CategoryDTO updatedCategory=categoryService.updateCategory(categoryDTO,categoryId);
            return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }



}
