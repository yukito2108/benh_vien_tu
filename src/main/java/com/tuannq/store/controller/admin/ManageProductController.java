package com.tuannq.store.controller.admin;

import com.tuannq.store.entity.Image;
import com.tuannq.store.model.dto.BrandDTO;
import com.tuannq.store.model.dto.CategoryDTO;
import com.tuannq.store.model.dto.ProductDTO;
import com.tuannq.store.model.request.ProductForm;
import com.tuannq.store.model.request.ProductSearchFormByAdmin;
import com.tuannq.store.model.response.PageResponse;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.service.BrandService;
import com.tuannq.store.service.CategoryService;
import com.tuannq.store.service.ImageService;
import com.tuannq.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ManageProductController {
    private final MessageSource messageSource;
    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    @Autowired
    public ManageProductController(
            MessageSource messageSource,
            ProductService productService,
            BrandService brandService,
            CategoryService categoryService,
            ImageService imageService
    ) {
        this.messageSource = messageSource;
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.imageService = imageService;
    }


    @GetMapping("/api/admin/products")
    public  ResponseEntity<SuccessResponse<PageResponse<ProductDTO>>> searchByAdmin(
            @Validated ProductSearchFormByAdmin form
    ) {
        var products = productService.searchByAdmin(form);
        return ResponseEntity.ok(new SuccessResponse<>(products));
    }


    @PostMapping("/api/admin/products")
    public ResponseEntity<SuccessResponse<ProductDTO>> createProduct(
            @Validated @RequestBody ProductForm form
    ) {
        var product = productService.createProduct(form);
        return ResponseEntity.ok(
                new SuccessResponse<>(
                        messageSource.getMessage("add.success", null, LocaleContextHolder.getLocale()),
                        new ProductDTO(product)));
    }

    @PutMapping("/api/admin/products/{id}")
    public ResponseEntity<SuccessResponse<ProductDTO>> Produupdatect(
            @Validated @RequestBody ProductForm form,
            @PathVariable Long id
    ) {
        var product = productService.updateProduct(id, form);
        return ResponseEntity.ok(
                new SuccessResponse<>(
                        messageSource.getMessage("update.success", null, LocaleContextHolder.getLocale()),
                        new ProductDTO(product)));
    }

    @DeleteMapping("/api/admin/products/{id}")
    public ResponseEntity<SuccessResponse<List<Long>>> deleteProduct(
            @PathVariable Long id
    ) {
        var ids = productService.deleteProduct(id);
        return ResponseEntity.ok(
                new SuccessResponse<>(
                        messageSource.getMessage("delete.success", null, LocaleContextHolder.getLocale()),
                        ids));

    }

/*
==========================================
View
==========================================
 */

    @GetMapping("/admin/products")
    public String getProductManagePage(
            Model model,
            @Validated ProductSearchFormByAdmin form
    ) {
        var products = productService.searchByAdmin(form);
        model.addAttribute("products", products);
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("formSearch", form);
        return "admin/product/list";
    }

    @GetMapping("/admin/products/create")
    public String addProductManagePage(Model model) {
        var brands = brandService.findAll().stream().map(BrandDTO::new).collect(Collectors.toList());
        var categoriesParent = categoryService.findByParent();
        var allCategories = categoryService.findAll()
                .stream().map(CategoryDTO::new)
                .collect(Collectors.toList());
        var images = imageService.getAll().stream().map(Image::getLink).collect(Collectors.toList());
        model.addAttribute("categoriesParent", categoriesParent);
        model.addAttribute("brands", brands);
        model.addAttribute("images", images);
        model.addAttribute("allCategories", allCategories);
        return "admin/product/create";
    }

    @GetMapping("/admin/products/{id}")
    public String updateProductManagePage(
            Model model,
            @PathVariable Long id
    ) {
        var product = productService.findById(id);
        var brands = brandService.findAll();
        var categoriesParent = categoryService.findByParent();
        var allCategories = categoryService.findAll()
                .stream().map(CategoryDTO::new)
                .collect(Collectors.toList());
        var images = imageService.getAll().stream().map(Image::getLink).collect(Collectors.toList());
        model.addAttribute("categoriesParent", categoriesParent);
        model.addAttribute("brands", brands);
        model.addAttribute("images", images);
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("product", product);
        return "admin/product/update";
    }

}
