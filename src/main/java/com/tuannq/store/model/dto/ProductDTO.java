package com.tuannq.store.model.dto;

import com.tuannq.store.entity.*;
import com.tuannq.store.util.ConverterUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends AbstractDTO {
    private ObjectResponse brand;
    private ObjectResponse category;
    private String code;
    private String name;
    private String slug;
    private String coverImage;
    private List<String> salientFeatures = new ArrayList<>();
    private List<String> productImages = new ArrayList<>();
    private String description;
    private Long priceFirst;
    private Long priceFinal;
    private Integer quantity;
    private Integer warrantyPeriod;
    private Boolean isAvailable;
    private Integer preOrder;
    private Integer dimensionWeight;
    private Integer dimensionHeight;
    private Integer dimensionWidth;
    private Integer dimensionLength;
    private String sku;
    private String metaTitle;
    private String metaKeyword;
    private String metaDescription;
    private String discount;

    public ProductDTO(Product product) {
        super(
                product.getId(),
                product.getCreatedBy(),
                product.getCreatedAt(),
                product.getModifiedBy(),
                product.getModifiedAt(),
                product.getIsDeleted()
        );
        this.brand = new ObjectResponse(product.getBrand().getId(), product.getBrand().getName());
        this.category = new ObjectResponse(product.getCategory().getId(), product.getCategory().getName());
        this.code = product.getCode();
        this.name = product.getName();
        this.slug = product.getSlug();
        this.coverImage = product.getCoverImage();
        this.salientFeatures = product.getSalientFeatures();
        this.productImages = product.getProductImages();
        this.description = product.getDescription();
        this.priceFirst = product.getPriceFirst();
        this.priceFinal = product.getPriceFinal();
        this.quantity = product.getQuantity();
        this.warrantyPeriod = product.getWarrantyPeriod();
        this.isAvailable = product.getIsAvailable();
        this.preOrder = product.getPreOrder();
        this.dimensionWeight = product.getDimensionWeight();
        this.dimensionHeight = product.getDimensionHeight();
        this.dimensionWidth = product.getDimensionWidth();
        this.dimensionLength = product.getDimensionLength();
        this.sku = product.getSku();
        this.metaTitle = product.getMetaTitle();
        this.metaKeyword = product.getMetaKeyword();
        this.metaDescription = product.getMetaDescription();
        if (product.getPriceFirst() != null && !Objects.equals(product.getPriceFirst(), product.getPriceFinal()))
            this.discount = ConverterUtils.formatDecimalToString((product.getPriceFirst() - product.getPriceFinal()) * 100.0 / product.getPriceFirst());
    }
}
