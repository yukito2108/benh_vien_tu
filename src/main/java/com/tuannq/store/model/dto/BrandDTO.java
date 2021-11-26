package com.tuannq.store.model.dto;

import com.tuannq.store.entity.Brand;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BrandDTO extends AbstractDTO {
    private String name;
    private String slug;
    private String thumbnail;
    private String description;
    private int productCount;
    private String metaTitle;
    private String metaKeyword;
    private String metaDescription;

    public BrandDTO(Brand brand) {
        super(
                brand.getId(),
                brand.getCreatedBy(),
                brand.getCreatedAt(),
                brand.getModifiedBy(),
                brand.getModifiedAt(),
                brand.getIsDeleted()
        );
        this.name = brand.getName();
        this.slug = brand.getSlug();
        this.thumbnail = brand.getThumbnail();
        this.description = brand.getDescription();
        this.productCount = brand.getProducts().size();
        this.metaTitle = brand.getMetaTitle();
        this.metaKeyword = brand.getMetaKeyword();
        this.metaDescription = brand.getMetaDescription();
    }
}
