package com.tuannq.store.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tuannq.store.entity.core.BaseEntity;
import com.tuannq.store.model.request.BrandForm;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Brand extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String slug;
    @Column(length = 1023)
    private String thumbnail;
    @Column(length = 2047)
    private String description;
    private String metaTitle;
    private String metaKeyword;
    @Column(length = 2047)
    private String metaDescription;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    Set<Product> products = new HashSet<>();

    public Brand(String concat, String concat1) {
        this.name = concat;
        this.slug = concat1;
    }

    public void setBrand(BrandForm form) {
        this.name = form.getName();
        this.slug = form.getSlug();
        this.thumbnail = form.getThumbnail();
        this.description = form.getDescription();

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

    public Brand(BrandForm form) {
        this.name = form.getName();
        this.slug = form.getSlug();
        this.thumbnail = form.getThumbnail();
        this.description = form.getDescription();

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
