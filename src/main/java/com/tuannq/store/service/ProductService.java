package com.tuannq.store.service;

import com.tuannq.store.entity.Product;
import com.tuannq.store.model.dto.ProductDTO;
import com.tuannq.store.model.request.*;
import com.tuannq.store.model.response.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    PageResponse<ProductDTO> searchByAdmin(ProductSearchFormByAdmin form);

    List<Product> findAll();

    Product findById(long productId);

    Product createProduct(ProductForm form);

    Product updateProduct(long productId, ProductForm form);

    List<Long> deleteProduct(long productId);

    Page<Product> searchPage(ProductSearchForm form);

    List<Product> search(ProductSearchForm form);

    List<ProductDTO> findByDiscount();

    List<ProductDTO> findByNew();

    List<ProductDTO> findByRandom();

    List<ProductDTO> bestSellingProduct();
}
