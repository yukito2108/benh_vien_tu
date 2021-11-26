package com.tuannq.store.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuannq.store.entity.core.BaseEntity;
import com.tuannq.store.model.request.ProductForm;
import com.tuannq.store.util.ConverterUtils;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class Product extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String code;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false, length = 511)
    private String slug;
    @Column(nullable = false, length = 511)
    private String coverImage;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> salientFeatures = new ArrayList<>();
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> productImages = new ArrayList<>();
    @Column(columnDefinition = "longtext")
    private String description;
    private Long priceFirst;
    @Column(nullable = false)
    private Long priceFinal;
    @Column(nullable = false)
    private Integer quantity;
    private Integer warrantyPeriod;
    @Column(nullable = false, columnDefinition = "BOOLEAN default true")
    private Boolean isAvailable = true;
    private Integer preOrder;
    private Integer dimensionWeight;
    private Integer dimensionHeight;
    private Integer dimensionWidth;
    private Integer dimensionLength;
    private String sku;
    private String metaTitle;
    private String metaKeyword;
    @Column(length = 2047)
    private String metaDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    Set<ProductSpecifications> productSpecifications = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    Set<OrderItem> orderItems = new HashSet<>();

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    Set<Promotion> promotions = new HashSet<>();

    public void setProduct(ProductForm form, Category category, Brand brand) {
        this.brand = brand;
        this.category = category;

        this.name = form.getName();
        this.slug = form.getSlug();
        this.coverImage = form.getCoverImage();
        this.salientFeatures = form.getSalientFeatures().stream().filter(data -> !Objects.equals(data.strip(), "")).collect(Collectors.toList());
        this.productImages = form.getProductImages();
        this.description = form.getDescription();
        this.priceFirst = ConverterUtils.convertStringToLong(form.getPriceFirst());
        this.priceFinal = ConverterUtils.convertStringToLong(form.getPriceFinal());
        this.quantity = ConverterUtils.convertStringToNumber(form.getQuantity());
        this.warrantyPeriod = ConverterUtils.convertStringToNumber(form.getWarrantyPeriod());
        this.isAvailable = form.getIsAvailable();
        this.preOrder = ConverterUtils.convertStringToNumber(form.getPreOrder());
        this.dimensionWeight = ConverterUtils.convertStringToNumber(form.getDimensionWeight());
        this.dimensionHeight = ConverterUtils.convertStringToNumber(form.getDimensionHeight());
        this.dimensionWidth = ConverterUtils.convertStringToNumber(form.getDimensionWidth());
        this.dimensionLength = ConverterUtils.convertStringToNumber(form.getDimensionLength());
        this.sku = form.getSku();

        if (form.getMetaTitle().isEmpty())
            this.metaTitle = form.getName();
        else this.metaTitle = form.getMetaTitle();

        if (form.getMetaKeyword().isEmpty())
            this.metaKeyword = form.getName();
        else this.metaKeyword = form.getMetaKeyword();

        if (form.getMetaDescription().isEmpty())
            if (form.getDescription().isEmpty() || form.getDescription().length() > 2000)
                this.metaDescription = form.getName();
            else this.metaDescription = form.getDescription();
        else this.metaDescription = form.getMetaDescription();

        this.setIsDeleted(form.getIsDeleted());
    }

    public Product(String code, ProductForm form, Category category, Brand brand) {
        this.code = code;
        this.brand = brand;
        this.category = category;

        this.name = form.getName();
        this.slug = form.getSlug();
        this.coverImage = form.getCoverImage();
        this.salientFeatures = form.getSalientFeatures().stream().filter(data -> !Objects.equals(data.strip(), "")).collect(Collectors.toList());
        this.productImages = form.getProductImages();
        this.description = form.getDescription();
        this.priceFirst = ConverterUtils.convertStringToLong(form.getPriceFirst());
        this.priceFinal = ConverterUtils.convertStringToLong(form.getPriceFinal());
        this.quantity = ConverterUtils.convertStringToNumber(form.getQuantity());
        this.warrantyPeriod = ConverterUtils.convertStringToNumber(form.getWarrantyPeriod());
        this.isAvailable = form.getIsAvailable();
        this.preOrder = ConverterUtils.convertStringToNumber(form.getPreOrder());
        this.dimensionWeight = ConverterUtils.convertStringToNumber(form.getDimensionWeight());
        this.dimensionHeight = ConverterUtils.convertStringToNumber(form.getDimensionHeight());
        this.dimensionWidth = ConverterUtils.convertStringToNumber(form.getDimensionWidth());
        this.dimensionLength = ConverterUtils.convertStringToNumber(form.getDimensionLength());
        this.sku = form.getSku();

        if (form.getMetaTitle().isEmpty())
            this.metaTitle = form.getName();
        else this.metaTitle = form.getMetaTitle();

        if (form.getMetaKeyword().isEmpty())
            this.metaKeyword = form.getName();
        else this.metaKeyword = form.getMetaKeyword();

        if (form.getMetaDescription().isEmpty())
            if (form.getDescription().isEmpty() || form.getDescription().length() > 2000)
                this.metaDescription = form.getName();
            else this.metaDescription = form.getDescription();
        else this.metaDescription = form.getMetaDescription();

        this.setIsDeleted(form.getIsDeleted());
    }
}
