package com.Springecom.EcomProject.service;

import com.Springecom.EcomProject.model.Category;
import com.Springecom.EcomProject.payload.CategoryDTO;
import com.Springecom.EcomProject.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize, String sortBy,String sortOrder);
     CategoryDTO createCategory(CategoryDTO categoryDTO);


    String deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
