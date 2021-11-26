package com.tuannq.store.repository;

import com.tuannq.store.entity.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SpecificationRepository extends JpaRepository<Specifications, Long> {

    @Query("select x1 from Specifications x1 " +
            "where lower(x1.name) like lower(concat('%',:name,'%')) " +
            "or lower(x1.code) like lower(concat('%',:name,'%')) ")
    List<Specifications> findByName(@Param("name") String name);
}
