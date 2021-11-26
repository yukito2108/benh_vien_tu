package com.tuannq.store.repository;


import com.tuannq.store.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select x1 from Category x1 " +
            "where :keyword is null " +
            "or lower(x1.name) like lower(concat('%',:keyword,'%')) ")
    Page<Category> search(@Param("keyword") String keyword, Pageable pageable);

    @Query("select x1 from Category x1 " +
            "where :keyword is null " +
            "or lower(x1.name) like lower(concat('%',:keyword,'%')) ")
    List<Category> search(@Param("keyword") String keyword);

    Category findByName(String name);

    Category findBySlug(String slug);

    @Query("select x1 from Category x1 " +
            "where x1.categoryParent is null ")
    List<Category> findByParent();
}
