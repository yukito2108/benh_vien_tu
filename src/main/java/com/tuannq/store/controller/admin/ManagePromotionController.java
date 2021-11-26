package com.tuannq.store.controller.admin;


import com.tuannq.store.model.request.PromotionAddForm;
import com.tuannq.store.model.request.PromotionAdminSearchForm;
import com.tuannq.store.service.ProductService;
import com.tuannq.store.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ManagePromotionController {
    private final PromotionService promotionService;
    private final ProductService productService;

    @Autowired
    public ManagePromotionController(
            PromotionService promotionService,
            ProductService productService
    ) {
        this.promotionService = promotionService;
        this.productService = productService;
    }

    @GetMapping("/admin/promotions")
    public String getPromotionManagePage(
            Model model,
           PromotionAdminSearchForm form
    ) {
        var result = promotionService.searchByAdmin(form);
        model.addAttribute("formSearch", form);
        model.addAttribute("promotions", result.getContent());
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getPageable().getPageNumber() + 1);

        return "admin/promotion/list";
    }

    @GetMapping("/admin/promotions/create")
    public String getPromotionCreatePage(Model model) {
        model.addAttribute("products", productService.findAll());

        return "admin/promotion/create";
    }

    @GetMapping("/admin/promotions/{id}")
    public String getPromotionDetailPage(Model model, @PathVariable long id) {
        var promotion = promotionService.getPromotionById(id);
        model.addAttribute("promotion", promotion);
        model.addAttribute("products", productService.findAll());

        return "admin/promotion/detail";
    }

    @PostMapping("/api/admin/promotions")
    public ResponseEntity<?> createPromotion(@Valid @RequestBody PromotionAddForm req) {
        var promotion = promotionService.createPromotion(req);

        return ResponseEntity.ok(promotion.getId());
    }

    @PutMapping("/api/admin/promotions/{id}")
    public ResponseEntity<?> updatePromotion(@Valid @RequestBody PromotionAddForm req, @PathVariable long id) {
        promotionService.updatePromotion(id, req);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/promotions/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable long id) {
        promotionService.deletePromotion(id);

        return ResponseEntity.ok("Xóa khuyến mãi thành công");
    }
}
