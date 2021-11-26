package com.tuannq.store.service.impl;

import com.tuannq.store.entity.Promotion;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.exception.BadRequestException;
import com.tuannq.store.exception.InternalServerException;
import com.tuannq.store.exception.NotFoundException;
import com.tuannq.store.model.dto.PromotionDTO;
import com.tuannq.store.model.request.PromotionAddForm;
import com.tuannq.store.model.request.PromotionAdminSearchForm;
import com.tuannq.store.repository.ProductRepository;
import com.tuannq.store.repository.PromotionRepository;
import com.tuannq.store.service.PromotionService;
import com.tuannq.store.util.ConverterUtils;
import com.tuannq.store.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tuannq.store.config.DefaultVariable.LIMIT;


@Component
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Page<PromotionDTO> searchByAdmin(PromotionAdminSearchForm form) {
        return promotionRepository.searchByAdmin(form,
                        new PageUtil(form.getPage(), LIMIT, form.getOrder(), form.getDirection()).getPageRequest())
                .map(PromotionDTO::new);
    }

    @Override
    public Promotion createPromotion(PromotionAddForm form) {
        PromotionAddForm.validate(form);
        var rs = promotionRepository.findByCouponCode(form.getCode());
        if (rs.isPresent())
            throw new ArgumentException("code", "code.exist");

        var promotion = new Promotion(form);

        var productIds = form.getProductIds()
                .stream()
                .map(ConverterUtils::convertStringToLong)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        var products = productRepository.findByIds(productIds);
        promotion.setProducts(products);

        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion updatePromotion(long id, PromotionAddForm form) {
        PromotionAddForm.validate(form);
        var promotionOptional = promotionRepository.findById(id);
        if (promotionOptional.isEmpty())
            throw new NotFoundException("not-found");
        var promotion = promotionOptional.get();

        var rs = promotionRepository.findByCouponCode(form.getCode());
        if (rs.map(discount -> !Objects.equals(discount.getId(), promotion.getId())).orElse(false))
            throw new ArgumentException("code", "code.exist");

        promotion.setData(form);

        var productIds = form.getProductIds()
                .stream()
                .map(ConverterUtils::convertStringToLong)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        var products = productRepository.findByIds(productIds);
        promotion.setProducts(products);

        return promotionRepository.save(promotion);
    }

    @Override
    public void deletePromotion(long id) {
        // Check exist promotion
        Optional<Promotion> rs = promotionRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Khuyến mãi không tồn tại");
        }

        // Check promotion in use
        int countUse = promotionRepository.checkPromotionInUse(rs.get().getCouponCode());
        if (countUse > 0) {
            throw new BadRequestException("Khuyến mãi đã được sử dụng. Không thể xóa");
        }

        try {
            promotionRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa khuyến mãi");
        }
    }

    @Override
    public Promotion getPromotionById(long id) {
        Optional<Promotion> rs = promotionRepository.findById(id);
        if (rs.isEmpty())
            throw new NotFoundException("Khuyến mãi không tồn tại");

        return rs.get();
    }

}
