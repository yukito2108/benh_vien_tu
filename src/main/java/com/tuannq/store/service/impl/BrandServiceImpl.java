package com.tuannq.store.service.impl;

import com.tuannq.store.entity.Brand;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.exception.NotFoundException;
import com.tuannq.store.model.dto.BrandDTO;
import com.tuannq.store.model.request.BrandForm;
import com.tuannq.store.model.request.BrandSearchFormByAdmin;
import com.tuannq.store.model.response.PageResponse;
import com.tuannq.store.repository.BrandRepository;
import com.tuannq.store.service.BrandService;
import com.tuannq.store.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tuannq.store.config.DefaultVariable.LIMIT;

@Component
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final MessageSource messageSource;

    @Autowired
    public BrandServiceImpl(
            BrandRepository repository,
            MessageSource messageSource
    ) {
        this.brandRepository = repository;
        this.messageSource = messageSource;
    }

    @Override
    public PageResponse<BrandDTO> search(BrandSearchFormByAdmin form) {
        var data= brandRepository.search(form, new PageUtil(form.getPage(), LIMIT, form.getOrder(), form.getDirection()).getPageRequest())
                .map(BrandDTO::new);
        return new PageResponse<>(data);
    }

    @Override
    public Brand addBrand(BrandForm form) {
        if (brandRepository.findByName(form.getName()) != null)
            throw new ArgumentException("name", messageSource.getMessage("name.exist", null, LocaleContextHolder.getLocale()));
        if (brandRepository.findBySlug(form.getSlug()) != null)
            throw new ArgumentException("slug", messageSource.getMessage("slug.exist", null, LocaleContextHolder.getLocale()));

        return brandRepository.save(new Brand(form));
    }

    @Override
    public Brand updateBrand(long id, BrandForm form) {
        var brandOpt = brandRepository.findById(id);
        if (brandOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.brand.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(id)));

        var byName = brandRepository.findByName(form.getName());
        if (byName != null && byName.getId() != id)
            throw new ArgumentException("name", messageSource.getMessage("name.exist", null, LocaleContextHolder.getLocale()));

        var bySlug = brandRepository.findBySlug(form.getSlug());
        if (bySlug != null && bySlug.getId() != id)
            throw new ArgumentException("slug", messageSource.getMessage("slug.exist", null, LocaleContextHolder.getLocale()));

        var brand = brandOpt.get();
        brand.setBrand(form);
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(long id) {
        if (brandRepository.findById(id).isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.brand.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(id)));

        brandRepository.deleteById(id);
    }

    @Override
    public Brand findById(long id) {
        var brandOpt = brandRepository.findById(id);
        if (brandOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.brand.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(id)));
        return brandOpt.get();
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
}
