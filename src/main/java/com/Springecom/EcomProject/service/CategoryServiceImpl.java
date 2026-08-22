package com.Springecom.EcomProject.service;

import com.Springecom.EcomProject.exception.APIException;
import com.Springecom.EcomProject.exception.ResourceNotFoundException;
import com.Springecom.EcomProject.model.Category;
import com.Springecom.EcomProject.payload.CategoryDTO;
import com.Springecom.EcomProject.payload.CategoryResponse;
import com.Springecom.EcomProject.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder) {
        //Sorting
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")?
            Sort.by(sortBy).ascending():
            Sort.by(sortBy).descending();

        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);//pagination
        Page<Category> categoryPage=categoryRepository.findAll(pageDetails);//Pagination
        List<Category> categories=categoryPage.getContent();//Pagination
        if(categories.isEmpty())
            throw new APIException("No Category added till now");
        List<CategoryDTO> categoryDTOS=categories.stream().map(category ->modelMapper.map(category, CategoryDTO.class)).toList();//DTO
        CategoryResponse categoryResponse=new CategoryResponse();
        //Pagination
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
    return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category CategoryFromDb=categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if(CategoryFromDb!=null)
            throw new APIException("Category with the name : "+categoryDTO.getCategoryName()+" is already exists");
        Category category=modelMapper.map(categoryDTO,Category.class);
        Category savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);

    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","Category",categoryId,"categoryId"));

           categoryRepository.delete(category);
        return "Category Id : "+ categoryId+" got deleted Successfully";
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
       Category savedcategory=categoryRepository.findById(categoryId).
               orElseThrow(()->new ResourceNotFoundException("Category","Category",categoryId,"categoryId"));
       Category category=modelMapper.map(categoryDTO,Category.class);//DTO to Entity
        category.setCategoryId(categoryId);
       savedcategory=categoryRepository.save(category);
       return modelMapper.map(savedcategory,CategoryDTO.class);//Entity to DTO
    }
}
