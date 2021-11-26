package com.tuannq.store.repository;

import com.tuannq.store.entity.ProductSpecifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpecificationRepository extends JpaRepository<ProductSpecifications, Long> {
}
