package com.example.demo.bishnu.service;



import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bishnu.entity.ProductEntity;
import com.example.demo.bishnu.model.AddProduct;
import com.example.demo.bishnu.model.UpdateProduct;

public interface ProductService {
  // add product 
  public void addProductMethod(AddProduct addProduct, MultipartFile file, Integer productUserId) throws IOException;
  
  // delete product
  public void deleteByProduct(Integer id) throws IOException;
  
  // update product by product id
  public void updateProductByProductId(int productId, UpdateProduct updateProduct, ProductEntity productEntity);
  
  // find product by product id
  public ProductEntity findByProductId(int productid);

}
