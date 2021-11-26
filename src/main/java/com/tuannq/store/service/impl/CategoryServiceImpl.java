package com.tuannq.store.service.impl;

import com.tuannq.store.entity.Category;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.exception.NotFoundException;
import com.tuannq.store.model.dto.CategoryDTO;
import com.tuannq.store.model.request.CategoryForm;
import com.tuannq.store.repository.CategoryRepository;
import com.tuannq.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Component
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;

    @Autowired
    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            MessageSource messageSource
    ) {
        this.categoryRepository = categoryRepository;
        this.messageSource = messageSource;
    }

    @Override
    public Page<Category> search(String keyword, Pageable pageable) {
        return categoryRepository.search(keyword, pageable);
    }

    @Override
    public List<Category> search(String keyword) {
        return categoryRepository.search(keyword);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryDTO> findByParent() {
        return categoryRepository.findByParent()
                .stream().map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Category addCategory(CategoryForm form) {
        if (List.of("", "0").contains(form.getCategoryParentId()) || form.getCategoryParentId() == null)
            return categoryRepository.save(new Category(form, null));

        var categoryParentOpt = categoryRepository.findById(Long.parseLong(form.getCategoryParentId()));
        if (categoryParentOpt.isEmpty())
            throw new ArgumentException(
                    "categoryParentId",
                    messageSource.getMessage("not-found.category.id", null, LocaleContextHolder.getLocale()).concat(form.getCategoryParentId())
            );

        return categoryRepository.save(new Category(form, categoryParentOpt.get()));
    }

    @Override
    public Category updateCategory(long id, CategoryForm form) {
        var categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.category.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(id)));

        var category = categoryOpt.get();
        category.setCategory(form, category.getCategoryParent());
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public List<Long> deleteCategory(long id) {
        var categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.category.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(id)));
        var data = listCategoryByParent(categoryOpt.get());

        categoryRepository.deleteAll(data);
        return data.stream().map(Category::getId).collect(Collectors.toList());
    }

    @Override
    public Category findById(long id) {
        var categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.category.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(id)));

        return categoryOpt.get();
    }

    /*
        public List<Category> listCategoryByParent(Category c) {
            var data = new ArrayList<Category>();
            data.add(c);
            if (c.getCategoryChild() != null)
                for (var c1 : c.getCategoryChild())
                    data.addAll(listCategoryByParent(c1));
            return data;
        }
    */
    public List<Category> listCategoryByParent(Category c) {
        List<Category> data = new ArrayList<>();
        if (c == null)
            return data;

        Stack<Category> stack = new Stack<>();
        var category = c;
        while (true) {
            data.add(category);
            for (var c1 : category.getCategoryChild())
                stack.push(c1);

            if (!stack.empty())
                category = stack.pop();
            else break;
        }
        return data;
    }

}
