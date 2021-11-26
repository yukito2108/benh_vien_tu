package com.tuannq.store.entity;

import com.tuannq.store.entity.core.BaseEntity;
import com.tuannq.store.model.request.SpecificationForm;
import com.tuannq.store.util.ConverterUtils;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Specifications extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String code;
    @Column(length = 2047)
    private String description;

    public void setData(SpecificationForm form) {
        this.code = ConverterUtils.toSlug(form.getName()) + "_" + Instant.now().toEpochMilli();
        this.name = form.getName();
        this.description = form.getDescription();
    }

    public Specifications(SpecificationForm form) {
        this.code = ConverterUtils.toSlug(form.getName()) + "_" + Instant.now().toEpochMilli();
        this.name = form.getName();
        this.description = form.getDescription();
    }
}
