package com.tuannq.store.service;

import com.tuannq.store.entity.Promotion;
import com.tuannq.store.model.dto.PromotionDTO;
import com.tuannq.store.model.request.PromotionAddForm;
import com.tuannq.store.model.request.PromotionAdminSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PromotionService {

    Page<PromotionDTO> searchByAdmin(PromotionAdminSearchForm form);

    Promotion createPromotion(PromotionAddForm req);

    Promotion updatePromotion(long id, PromotionAddForm req);

    void deletePromotion(long id);

    Promotion getPromotionById(long id);
}
