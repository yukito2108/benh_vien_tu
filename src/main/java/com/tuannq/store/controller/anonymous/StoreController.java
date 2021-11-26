package com.tuannq.store.controller.anonymous;

import com.tuannq.store.entity.Category;
import com.tuannq.store.entity.Product;
import com.tuannq.store.model.dto.CategoryDTO;
import com.tuannq.store.model.dto.OrderItemDTO;
import com.tuannq.store.model.dto.ProductDTO;
import com.tuannq.store.model.request.ProductSearchForm;
import com.tuannq.store.model.response.PageResponse;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.repository.CategoryRepository;
import com.tuannq.store.service.BrandService;
import com.tuannq.store.service.CategoryService;
import com.tuannq.store.service.OrderItemService;
import com.tuannq.store.service.ProductService;
import com.tuannq.store.util.AuthUtils;
import com.tuannq.store.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class StoreController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final AuthUtils authUtils;
    private final OrderItemService orderItemService;
    private final ConverterUtils converterUtils;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public StoreController(ProductService productService, CategoryService categoryService, BrandService brandService, AuthUtils authUtils, OrderItemService orderItemService, ConverterUtils converterUtils) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.authUtils = authUtils;
        this.orderItemService = orderItemService;
        this.converterUtils = converterUtils;
    }

    @GetMapping("/shopping")
    public String home(
            Model model
    ) {
        var userOpt = authUtils.getUser();
        if (userOpt.isPresent()) {
            var items = orderItemService.getCartByUserId(userOpt.get().getId())
                    .stream()
                    .map(OrderItemDTO::new)
                    .collect(Collectors.toList());

            var totalAmount = items.stream().map(OrderItemDTO::getAmount).reduce(Long::sum).orElse(0L);
            model.addAttribute("cart", items);
            model.addAttribute("totalAmount", totalAmount);
        } else {
            model.addAttribute("cart", new ArrayList<>());
            model.addAttribute("totalAmount", 0);
        }
        model.addAttribute("header_categories", categoryService.findByParent());
        model.addAttribute("converterUtils", converterUtils);
        model.addAttribute("discountProducts", productService.findByDiscount());
        model.addAttribute("newProduct", productService.findByNew());
        model.addAttribute("randomProduct", productService.findByRandom());
        model.addAttribute("bestSellingProduct", productService.bestSellingProduct());
        model.addAttribute("page", "home");

        return "shop/homepage";
    }

    @GetMapping("/")
    public String home1() {
        return "medikal/index";
    }

    @GetMapping("/product/{slug}/{id}")
    public String product(
            Model model,
            @PathVariable String slug,
            @PathVariable Long id
    ) {
        var userOpt = authUtils.getUser();
        if (userOpt.isPresent()) {
            var items = orderItemService.getCartByUserId(userOpt.get().getId())
                    .stream()
                    .map(OrderItemDTO::new)
                    .collect(Collectors.toList());

            var totalAmount = items.stream().map(OrderItemDTO::getAmount).reduce(Long::sum).orElse(0L);
            model.addAttribute("cart", items);
            model.addAttribute("totalAmount", totalAmount);
        } else {
            model.addAttribute("cart", new ArrayList<>());
            model.addAttribute("totalAmount", 0);
        }

        model.addAttribute("converterUtils", converterUtils);
        model.addAttribute("randomProduct", productService.findByRandom());
        model.addAttribute("header_categories", categoryService.findByParent());

        var productEntity = productService.findById(id);
        model.addAttribute("product", new ProductDTO(productEntity));
        return "shop/product/detail";
    }

    @GetMapping("/search")
    public String search(
            Model model,
            @Validated ProductSearchForm form
    ) {
        var userOpt = authUtils.getUser();
        if (userOpt.isPresent()) {
            var items = orderItemService.getCartByUserId(userOpt.get().getId())
                    .stream()
                    .map(OrderItemDTO::new)
                    .collect(Collectors.toList());

            var totalAmount = items.stream().map(OrderItemDTO::getAmount).reduce(Long::sum).orElse(0L);
            model.addAttribute("cart", items);
            model.addAttribute("totalAmount", totalAmount);
        } else {
            model.addAttribute("cart", new ArrayList<>());
            model.addAttribute("totalAmount", 0);
        }
        model.addAttribute("header_categories", categoryService.findByParent());

        var products = new PageResponse<>(productService.searchPage(form).map(ProductDTO::new));

        var data = productService.search(form);
        var brands = data
                .stream()
                .map(Product::getBrand)
                .distinct()
                .collect(Collectors.toList());
        var categories = data
                .stream()
                .map(Product::getCategory)
                .distinct()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("products", products);
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        model.addAttribute("form", form);
        model.addAttribute("keyword", form.getKeyword());

        model.addAttribute("converterUtils", converterUtils);
        return "shop/product/search";
    }

    @GetMapping("/danh-muc/{slug}")
    public String danhMuc(
            Model model,
            @Validated ProductSearchForm form,
            HttpServletResponse httpServletResponse,
            @PathVariable String slug
    ) throws IOException {
        var category = categoryRepository.findBySlug(slug);
        if (category == null) {
            httpServletResponse.sendRedirect("/");
            return null;
        }
        form.setCategories(List.of(category.getId().toString()));

        var userOpt = authUtils.getUser();
        if (userOpt.isPresent()) {
            var items = orderItemService.getCartByUserId(userOpt.get().getId())
                    .stream()
                    .map(OrderItemDTO::new)
                    .collect(Collectors.toList());

            var totalAmount = items.stream().map(OrderItemDTO::getAmount).reduce(Long::sum).orElse(0L);
            model.addAttribute("cart", items);
            model.addAttribute("totalAmount", totalAmount);
        } else {
            model.addAttribute("cart", new ArrayList<>());
            model.addAttribute("totalAmount", 0);
        }
        model.addAttribute("header_categories", categoryService.findByParent());

        var products = new PageResponse<>(productService.searchPage(form).map(ProductDTO::new));

        var data = productService.search(form);
        var brands = data
                .stream()
                .map(Product::getBrand)
                .distinct()
                .collect(Collectors.toList());
        var categories = data
                .stream()
                .map(Product::getCategory)
                .distinct()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());

        model.addAttribute("products", products);
        model.addAttribute("brands", brands);
        model.addAttribute("category", new CategoryDTO(category));
        model.addAttribute("form", form);
        model.addAttribute("keyword", form.getKeyword());

        model.addAttribute("converterUtils", converterUtils);
        return "shop/product/danh-muc";
    }

    @GetMapping("/api/search")
    public ResponseEntity<SuccessResponse<PageResponse<ProductDTO>>> search(
            @Validated ProductSearchForm form
    ) {
        var data = productService.searchPage(form)
                .map(ProductDTO::new);
        var products = new PageResponse<>(data);

        return ResponseEntity.ok(new SuccessResponse<>(products));
    }
}
