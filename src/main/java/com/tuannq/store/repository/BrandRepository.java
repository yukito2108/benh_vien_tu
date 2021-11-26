package com.tuannq.store.repository;

import com.tuannq.store.entity.Brand;
import com.tuannq.store.model.request.BrandSearchFormByAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("select x1 from Brand x1 " +
            "where (:#{#form.name} is null or :#{#form.name} = '' or lower(x1.name) like lower(concat('%', lower(:#{#form.name}), '%' ) )) ")
    Page<Brand> search(BrandSearchFormByAdmin form, Pageable pageable);

    Brand findByName(String name);

    Brand findBySlug(String slug);
}
