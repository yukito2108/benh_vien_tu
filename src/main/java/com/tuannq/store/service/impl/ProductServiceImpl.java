package com.tuannq.store.service.impl;

import com.tuannq.store.entity.Product;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.exception.NotFoundException;
import com.tuannq.store.model.dto.ProductDTO;
import com.tuannq.store.model.request.*;
import com.tuannq.store.model.response.*;
import com.tuannq.store.repository.BrandRepository;
import com.tuannq.store.repository.CategoryRepository;
import com.tuannq.store.repository.ProductRepository;
import com.tuannq.store.service.ProductService;
import com.tuannq.store.util.ConverterUtils;
import com.tuannq.store.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tuannq.store.config.DefaultVariable.LIMIT;


@Component
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;
    private final ConverterUtils converterUtils;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            BrandRepository brandRepository,
            CategoryRepository categoryRepository,
            ConverterUtils converterUtils,
            MessageSource messageSource
    ) {
        this.converterUtils = converterUtils;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
        this.messageSource = messageSource;
    }

    @Override
    public PageResponse<ProductDTO> searchByAdmin(ProductSearchFormByAdmin form) {
        var data = productRepository.searchByAdmin(
                form,
                new PageUtil(form.getPage(), LIMIT, form.getOrder(), form.getDirection()).getPageRequest()
        ).map(ProductDTO::new);
        return new PageResponse<>(data);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(long productId) {
        var productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.product.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(productId)));

        return productOpt.get();
    }

    @Override
    public Product createProduct(ProductForm form) {

        var brandOpt = brandRepository.findById(Long.valueOf(form.getBrandId()));
        if (brandOpt.isEmpty())
            throw new ArgumentException("brandId", messageSource.getMessage("not-found.brand.id", null, LocaleContextHolder.getLocale()).concat(form.getCategoryId()));

        var categoryOpt = categoryRepository.findById(Long.valueOf(form.getCategoryId()));
        if (categoryOpt.isEmpty())
            throw new ArgumentException("categoryId", messageSource.getMessage("not-found.category.id", null, LocaleContextHolder.getLocale()).concat(form.getCategoryId()));

        if (productRepository.findByName(form.getName()).isPresent())
            throw new ArgumentException("name", messageSource.getMessage("name.exist", null, LocaleContextHolder.getLocale()));
        if (productRepository.findBySlug(form.getSlug()).isPresent())
            throw new ArgumentException("slug", messageSource.getMessage("slug.exist", null, LocaleContextHolder.getLocale()));

        Product product = new Product(UUID.randomUUID().toString(), form, categoryOpt.get(), brandOpt.get());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(long productId, ProductForm form) {
        var productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.product.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(productId)));

        var brandOpt = brandRepository.findById(Long.valueOf(form.getBrandId()));
        if (brandOpt.isEmpty())
            throw new ArgumentException("brandId", messageSource.getMessage("not-found.brand.id", null, LocaleContextHolder.getLocale()).concat(form.getCategoryId()));

        var categoryOpt = categoryRepository.findById(Long.valueOf(form.getCategoryId()));
        if (categoryOpt.isEmpty())
            throw new ArgumentException("categoryId", messageSource.getMessage("not-found.category.id", null, LocaleContextHolder.getLocale()).concat(form.getCategoryId()));

        var byName = productRepository.findByName(form.getName());
        if (byName.map(p -> p.getId() != productId).orElse(false))
            throw new ArgumentException("name", messageSource.getMessage("name.exist", null, LocaleContextHolder.getLocale()));

        var bySlug = productRepository.findBySlug(form.getSlug());
        if (bySlug.map(p -> p.getId() != productId).orElse(false))
            throw new ArgumentException("slug", messageSource.getMessage("slug.exist", null, LocaleContextHolder.getLocale()));

        Product product = productOpt.get();
        product.setProduct(form, categoryOpt.get(), brandOpt.get());
        return productRepository.save(product);
    }

    @Override
    public List<Long> deleteProduct(long productId) {
        var productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.product.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(productId)));

        productRepository.deleteById(productId);
        return List.of(productId);
    }

    @Override
    public Page<Product> searchPage(ProductSearchForm form) {
        List<Long> brands = form.getBrands()
                .stream()
                .map(ConverterUtils::convertStringToLong)
                .distinct()
                .collect(Collectors.toList());

        List<Long> categories = form.getCategories()
                .stream()
                .map(ConverterUtils::convertStringToLong)
                .distinct()
                .collect(Collectors.toList());

        String[] split = form.getPriceRange().split("-");
        Long formPrice = ConverterUtils.convertStringToLong(split[0]);
        Long toPrice = ConverterUtils.convertStringToLong(split.length > 1 ? split[1] : null);

        return productRepository.search(
                form.getKeyword(),
                brands,
                categories,
                formPrice,
                toPrice,
                new PageUtil(form.getPage(), LIMIT, form.getOrder(), form.getDirection()).getPageRequest()
        );
    }

    @Override
    public List<Product> search(ProductSearchForm form) {
        List<Long> brands = form.getBrands()
                .stream()
                .map(ConverterUtils::convertStringToLong)
                .distinct()
                .collect(Collectors.toList());

        List<Long> categories = form.getCategories()
                .stream()
                .map(ConverterUtils::convertStringToLong)
                .distinct()
                .collect(Collectors.toList());

        String[] split = form.getPriceRange().split("-");
        Long formPrice = ConverterUtils.convertStringToLong(split[0]);
        Long toPrice = ConverterUtils.convertStringToLong(split.length > 1 ? split[1] : null);

        return productRepository.search(
                form.getKeyword(),
                brands,
                categories,
                formPrice,
                toPrice
        );
    }

    @Override
    public List<ProductDTO> findByDiscount() {
        return productRepository.findByDiscount(PageRequest.of(0, 10)).stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findByNew() {
        return productRepository.findByNew(PageRequest.of(0, 10)).stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findByRandom() {
        return productRepository.findByRandom(PageRequest.of(0, 8)).stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> bestSellingProduct() {
        return productRepository.bestSellingProduct(PageRequest.of(0, 10)).stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }
}
