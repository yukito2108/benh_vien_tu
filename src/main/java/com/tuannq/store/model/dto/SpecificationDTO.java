package com.tuannq.store.model.dto;

import com.tuannq.store.entity.Specifications;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecificationDTO extends AbstractDTO {
    private String name;
    private String code;
    private String description;

    public SpecificationDTO(Specifications specification) {
        super(
                specification.getId(),
                specification.getCreatedBy(),
                specification.getCreatedAt(),
                specification.getModifiedBy(),
                specification.getModifiedAt(),
                specification.getIsDeleted()
        );
        this.name = specification.getName();
        this.code = specification.getCode();
        this.description = specification.getDescription();
    }
}
