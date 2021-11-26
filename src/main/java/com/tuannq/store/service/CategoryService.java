package com.tuannq.store.service;

import com.tuannq.store.entity.Category;
import com.tuannq.store.model.dto.CategoryDTO;
import com.tuannq.store.model.request.CategoryForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Page<Category> search(String keyword, Pageable pageable);

    List<Category> search(String keyword);

    List<Category> findAll();

    List<CategoryDTO> findByParent();

    Category addCategory(CategoryForm form);

    Category updateCategory(long id, CategoryForm form);

    List<Long> deleteCategory(long id);

    Category findById(long id);
}
