package com.tuannq.store.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalExaminationResultForm {
    @NotBlank(message = "not-null")
    private String medicalExaminationResults;
}
