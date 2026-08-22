package com.Springecom.EcomProject.service;

import com.Springecom.EcomProject.exception.APIException;
import com.Springecom.EcomProject.exception.ResourceNotFoundException;
import com.Springecom.EcomProject.model.Category;
import com.Springecom.EcomProject.model.Product;
import com.Springecom.EcomProject.payload.ProductDTO;
import com.Springecom.EcomProject.payload.ProductResponse;
import com.Springecom.EcomProject.repository.CategoryRepository;
import com.Springecom.EcomProject.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
   private FileService fileService;
    @Value("${project.image}")
    private String path;
    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Category category=categoryRepository.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","Category",categoryId,"categoryId"));
        boolean isProductNotPresent = true;
        List<Product> products=category.getProducts();
        for(Product product:products){
            if(product.getProductName().equals(productDTO.getProductName())){
                isProductNotPresent = false;
                break;
            }
        }
        if(isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setCategory(category);
            product.setImage("default.png");
            double specialPrice = product.getPrice() - (product.getDiscount() * 0.01) * product.getPrice();
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }
        else{
            throw new APIException("Product Already exist");
        }
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
        Sort sortProductByOrder=sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sortProductByOrder);
        Page<Product> pageProducts=productRepository.findAll(pageable);
        List<Product> products=pageProducts.getContent();
        List<ProductDTO> productDTOS=products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class)).toList();
        if(products.isEmpty()){
            throw new APIException("Product Not Found");
        }
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setTotalElements(pageProducts.getTotalElements());

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, Long categoryId) {
        Category category=categoryRepository.
                findById(categoryId).orElseThrow(()->
                        new ResourceNotFoundException("Category","Category",categoryId,"categoryId"));
        Sort sortProductByOrder=sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sortProductByOrder);
        Page<Product> pageProducts= (Page<Product>) productRepository.findByCategoryOrderByPrice(category,pageable);
        List<Product> products=pageProducts.getContent();
        List<ProductDTO> productDTOS=products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setTotalElements(pageProducts.getTotalElements());

        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getProductByKeyword(Integer pageNumber, Integer pageSize, String keyword, String sortOrder, String s) {
        List<Sort.Order> sortBy = List.of();
        Sort sortProductByOrder=sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sortProductByOrder);
        Page<Product> pageProducts= (Page<Product>) productRepository.findByProductNameLikeIgnoreCase(keyword,pageable);
        List<Product> products=pageProducts.getContent();
        List<ProductDTO>productDTOS=products.stream()
                .map(product->modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setTotalElements(pageProducts.getTotalElements());

        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO UpdateProduct(ProductDTO product, Long productId) {
        //Get Existing Product from DB
        Product productFromDb=productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","Product",productId,"productId"));
        //Update the product info with the one in user body
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setDiscount(product.getDiscount());
        double specialPrice=product.getPrice()-(product.getDiscount()*0.01)*product.getPrice();
        productFromDb.setSpecialPrice(specialPrice);
        //save to Database
        Product savedProduct=productRepository.save(productFromDb);
        return modelMapper.map(savedProduct,ProductDTO.class);

    }

    @Override
    public ProductDTO DeleteProduct(Long productId) {
        Product productFromDB = productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("Product", "Product", productId, "productId"));
        productRepository.delete(productFromDB);
        return modelMapper.map(productFromDB, ProductDTO.class);
    }

    @Override
    public ProductDTO UpdateProductImage(Long productId, MultipartFile image) throws IOException {
    //Get Product from DB
        Product productFromDB=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "Product", productId, "productId"));
      //Upload Image to Server and get the  filename of uploaded image
        String fileName=fileService.uploadImage(path, image);
        //Updating new file name to the product
        productFromDB.setImage(fileName);
        //Save the Updated Product
        Product updatedProduct=productRepository.save(productFromDB);
        //Return DTO after mapping product to DTO
        return modelMapper.map(productFromDB,ProductDTO.class);
    }




}
