package com.tuannq.store.controller.admin;

import com.tuannq.store.entity.Category;
import com.tuannq.store.entity.Image;
import com.tuannq.store.model.dto.CategoryDTO;
import com.tuannq.store.model.request.CategoryForm;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.service.CategoryService;
import com.tuannq.store.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ManageCategoryController {
    private final MessageSource messageSource;
    private final CategoryService categoryService;
    private final ImageService imageService;

    @Autowired
    public ManageCategoryController(
            MessageSource messageSource,
            CategoryService categoryService,
            ImageService imageService
    ) {
        this.messageSource = messageSource;
        this.categoryService = categoryService;
        this.imageService = imageService;
    }

    @GetMapping("/admin/categories")
    public String getCategoryManagePage(
            Model model,
            @RequestParam(value = "search", required = false) String keyword
    ) {
        var categories = categoryService.search(keyword).stream()
                .filter(c -> c.getCategoryParent() == null)
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);

        var images = imageService.getAll().stream().map(Image::getLink).collect(Collectors.toList());
        model.addAttribute("images", images);

        var allCategories = categoryService.findAll().stream().map(CategoryDTO::new)
                .collect(Collectors.toList());
        model.addAttribute("allCategories", allCategories);
        return "admin/category/list";
    }

    @GetMapping("/api/admin/categories/{id}")
    public ResponseEntity<SuccessResponse<CategoryDTO>> getDetailCategory(
            @PathVariable("id") long id
    ) {
        var category = categoryService.findById(id);
        return ResponseEntity.ok(
                new SuccessResponse<>("", new CategoryDTO(category)));
    }

    @GetMapping("/api/admin/categories/all")
    public ResponseEntity<SuccessResponse<List<CategoryDTO>>> getDetailCategory() {
        var categories = categoryService.findAll()
                .stream().map(CategoryDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                new SuccessResponse<>("", categories));
    }

    @DeleteMapping("/api/admin/categories/{id}")
    public ResponseEntity<SuccessResponse<List<Long>>> deleteCategory(
            @PathVariable("id") long id
    ) {
        var ids = categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                new SuccessResponse<>(messageSource.getMessage("delete.success", null, LocaleContextHolder.getLocale()), ids));
    }

    @PostMapping("/api/admin/categories")
    public ResponseEntity<SuccessResponse<CategoryDTO>> createCategory(
            @Validated @RequestBody CategoryForm form
    ) {
        Category category = categoryService.addCategory(form);

        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("add.success", null, LocaleContextHolder.getLocale()),
                new CategoryDTO(category)
        ));
    }

    @PutMapping("/api/admin/categories/{id}")
    public ResponseEntity<SuccessResponse<CategoryDTO>> createCategory(
            @Validated @RequestBody CategoryForm form,
            @PathVariable("id") long id
    ) {
        Category category = categoryService.updateCategory(id, form);

        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("update.success", null, LocaleContextHolder.getLocale()),
                new CategoryDTO(category)
        ));
    }

}
