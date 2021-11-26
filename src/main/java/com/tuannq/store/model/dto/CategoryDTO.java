package com.tuannq.store.model.dto;

import com.tuannq.store.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO extends AbstractDTO {
    private String name;
    private String slug;
    private String banner;
    private String description;
    private Set<CategoryDTO> categoryChild = new HashSet<>();
    private Long categoryParentId;
    private long productCount;

    private String metaTitle;
    private String metaKeyword;
    private String metaDescription;


    public CategoryDTO(Category category) {
        super(
                category.getId(),
                category.getCreatedBy(),
                category.getCreatedAt(),
                category.getModifiedBy(),
                category.getModifiedAt(),
                category.getIsDeleted()
        );
        this.name = category.getName();
        this.slug = category.getSlug();
        this.banner = category.getBanner();
        this.description = category.getDescription();
        this.productCount = category.getProducts().size();
        this.categoryParentId = Optional.ofNullable(category.getCategoryParent()).map(Category::getId).orElse(null);
        this.categoryChild = category.getCategoryChild()
                .stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toSet());

        this.metaTitle = category.getMetaTitle();
        this.metaKeyword = category.getMetaKeyword();
        this.metaDescription = category.getMetaDescription();
    }
}
