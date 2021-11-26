package com.tuannq.store.service;

import com.tuannq.store.entity.Specifications;
import com.tuannq.store.model.dto.SpecificationDTO;
import com.tuannq.store.model.request.SpecificationForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpecificationService {
    List<Specifications> findByName(String name);

    List<SpecificationDTO> findAll();

    SpecificationDTO findById(long id);

    SpecificationDTO create(SpecificationForm form);

    SpecificationDTO update(Long id, SpecificationForm form);

    void delete(long id);
}
