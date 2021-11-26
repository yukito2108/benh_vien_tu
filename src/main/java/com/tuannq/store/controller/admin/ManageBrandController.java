package com.tuannq.store.controller.admin;

import com.tuannq.store.entity.Brand;
import com.tuannq.store.entity.Image;
import com.tuannq.store.model.dto.BrandDTO;
import com.tuannq.store.model.dto.UserDTO;
import com.tuannq.store.model.request.BrandForm;
import com.tuannq.store.model.request.BrandSearchFormByAdmin;
import com.tuannq.store.model.response.PageResponse;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.service.BrandService;
import com.tuannq.store.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class ManageBrandController {
    private final BrandService brandService;
    private final MessageSource messageSource;
    private final ImageService imageService;

    @Autowired
    public ManageBrandController(
            BrandService brandService,
            ImageService imageService,
            MessageSource messageSource
    ) {
        this.brandService = brandService;
        this.imageService = imageService;
        this.messageSource = messageSource;
    }

    @GetMapping("/admin/brands")
    public String getBrandManagePage(
            Model model,
           @Validated BrandSearchFormByAdmin form
    ) {
        var brands = brandService.search(form);
        model.addAttribute("brands", brands);
        model.addAttribute("formSearch", form);

        var images = imageService.getAll().stream().map(Image::getLink).collect(Collectors.toList());
        model.addAttribute("images", images);

        return "admin/brand/list";
    }

    @GetMapping("/api/admin/brands")
    public ResponseEntity<SuccessResponse<PageResponse<BrandDTO>>> search(
           @Validated BrandSearchFormByAdmin form
    ) {
        var brands = brandService.search(form);

        return ResponseEntity.ok(new SuccessResponse<>(brands));
    }

    @GetMapping("/api/admin/brands/{id}")
    public ResponseEntity<SuccessResponse<BrandDTO>> getDetailBrand(
            @PathVariable long id
    ) {
        var brand = brandService.findById(id);

        return ResponseEntity.ok(new SuccessResponse<>(
                null,
                new BrandDTO(brand)
        ));
    }

    @PostMapping("/api/admin/brands")
    public ResponseEntity<SuccessResponse<BrandDTO>> createBrand(
            @Validated @RequestBody BrandForm form
    ) {
        Brand brand = brandService.addBrand(form);

        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("add.success", null, LocaleContextHolder.getLocale()),
                new BrandDTO(brand)
        ));
    }

    @PutMapping("/api/admin/brands/{id}")
    public ResponseEntity<SuccessResponse<BrandDTO>> updateBrand(
            @Validated @RequestBody BrandForm form,
            @PathVariable long id
    ) {
        var brand = brandService.updateBrand(id, form);

        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("update.success", null, LocaleContextHolder.getLocale()),
                new BrandDTO(brand)
        ));
    }

    @DeleteMapping("/api/admin/brands/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteBrand(
            @PathVariable long id
    ) {
        brandService.deleteBrand(id);

        return ResponseEntity.ok(new SuccessResponse<>(messageSource.getMessage("delete.success", null, LocaleContextHolder.getLocale()), null));
    }
}
