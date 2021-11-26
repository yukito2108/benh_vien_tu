package com.tuannq.store.service;

import com.tuannq.store.entity.Brand;
import com.tuannq.store.model.dto.BrandDTO;
import com.tuannq.store.model.request.BrandForm;
import com.tuannq.store.model.request.BrandSearchFormByAdmin;
import com.tuannq.store.model.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    PageResponse<BrandDTO> search(BrandSearchFormByAdmin form);

    Brand addBrand(BrandForm form);

    Brand updateBrand(long id, BrandForm form);

    void deleteBrand(long id);

    Brand findById(long id);

    List<Brand> findAll();
}
