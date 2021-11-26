package com.tuannq.store.service.impl;

import com.tuannq.store.entity.Specifications;
import com.tuannq.store.exception.NotFoundException;
import com.tuannq.store.model.dto.SpecificationDTO;
import com.tuannq.store.model.request.SpecificationForm;
import com.tuannq.store.repository.SpecificationRepository;
import com.tuannq.store.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SpecificationServiceImpl implements SpecificationService {
    private final SpecificationRepository specificationRepository;

    @Autowired
    public SpecificationServiceImpl(SpecificationRepository specificationRepository) {
        this.specificationRepository = specificationRepository;
    }

    @Override
    public List<Specifications> findByName(String name) {
        return specificationRepository.findByName(name);
    }

    @Override
    public List<SpecificationDTO> findAll() {
        return specificationRepository.findAll()
                .stream().map(SpecificationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public SpecificationDTO findById(long id) {
        var specification = specificationRepository.findById(id);
        if (specification.isEmpty())
            throw new NotFoundException("not-found");
        return specification.map(SpecificationDTO::new).get();
    }

    @Override
    public SpecificationDTO create(SpecificationForm form) {
        var specification = new Specifications(form);
        Specifications save = specificationRepository.save(specification);
        return new SpecificationDTO(save);
    }

    @Override
    public SpecificationDTO update(Long id, SpecificationForm form) {
        var specificationsOptional = specificationRepository.findById(id);
        if (specificationsOptional.isEmpty())
            throw new NotFoundException("not-found");
        var specification = specificationsOptional.get();
        specification.setData(form);
        return new SpecificationDTO(specificationRepository.save(specification));
    }

    @Override
    public void delete(long id) {
        var specificationsOptional = specificationRepository.findById(id);
        if (specificationsOptional.isEmpty())
            throw new NotFoundException("not-found");
        specificationRepository.deleteById(id);
    }
}
