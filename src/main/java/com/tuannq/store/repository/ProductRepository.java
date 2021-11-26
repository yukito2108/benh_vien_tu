package com.tuannq.store.repository;

import com.tuannq.store.entity.Product;
import com.tuannq.store.model.request.ProductSearchFormByAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    Optional<Product> findByCode(String code);

    Optional<Product> findBySlug(String slug);

    @Query("select x1 from Product x1 " +
            " where x1.priceFirst is not null " +
            " order by ((coalesce(x1.priceFirst, 0) - x1.priceFinal) / coalesce(x1.priceFirst, 1))  desc, x1.modifiedAt desc")
    List<Product> findByDiscount(Pageable pageable);

    @Query("select x1 from Product x1 " +
            " order by x1.createdAt desc")
    List<Product> findByNew(Pageable pageable);

    @Query("select x1 from Product x1 order by rand()")
    List<Product> findByRandom(Pageable pageable);

    @Query("select x1.product from OrderItem x1 " +
            " group by x1.product " +
            " order by sum(x1.quantity) desc ")
    List<Product> bestSellingProduct(Pageable pageable);

    @Query("SELECT x1 FROM Product x1 " +
            "WHERE (:#{#keyword} is null or :#{#keyword} = '' " +
            "   or lower(x1.description) like lower(concat('%',:#{#keyword},'%')) " +
            "   or lower(x1.name) like lower(concat('%',:#{#keyword},'%')) " +
            "   or lower(x1.code) like lower(concat('%',:#{#keyword},'%')) " +
            "   or :#{#keyword} in x1.salientFeatures )" +
            "AND (COALESCE(:#{#brands}) IS NULL OR  (x1.brand.id IN :#{#brands} )) " +
            "AND (COALESCE(:#{#categories}) IS NULL or (x1.category.id IN :#{#categories} )) " +
            "AND (:#{#fromPrice} is null or x1.priceFinal > :#{#fromPrice} or x1.priceFinal = :#{#fromPrice}) " +
            "AND (:#{#toPrice} is null or x1.priceFinal < :#{#toPrice} or x1.priceFinal = :#{#toPrice})")
    Page<Product> search(
            @Param("keyword") String keyword,
            @Param("brands") List<Long> brands,
            @Param("categories") List<Long> categories,
            @Param("fromPrice") Long fromPrice,
            @Param("toPrice") Long toPrice,
            Pageable pageable
    );

    @Query("SELECT x1 FROM Product x1 " +
            "WHERE (:#{#keyword} is null or :#{#keyword} = '' " +
            "   or lower(x1.description) like lower(concat('%',:#{#keyword},'%')) " +
            "   or lower(x1.name) like lower(concat('%',:#{#keyword},'%')) " +
            "   or lower(x1.code) like lower(concat('%',:#{#keyword},'%')) " +
            "   or :#{#keyword} in x1.salientFeatures )" +
            "AND (COALESCE(:#{#brands}) IS NULL OR  (x1.brand.id IN :#{#brands} )) " +
            "AND (COALESCE(:#{#categories}) IS NULL or (x1.category.id IN :#{#categories} )) " +
            "AND (:#{#fromPrice} is null or x1.priceFinal > :#{#fromPrice} or x1.priceFinal = :#{#fromPrice}) " +
            "AND (:#{#toPrice} is null or x1.priceFinal < :#{#toPrice} or x1.priceFinal = :#{#toPrice})")
    List<Product> search(
            @Param("keyword") String keyword,
            @Param("brands") List<Long> brands,
            @Param("categories") List<Long> categories,
            @Param("fromPrice") Long fromPrice,
            @Param("toPrice") Long toPrice
    );

    @Query("select x1 from Product  x1 " +
            " where (:#{#form.code} is null or :#{#form.code} = '' or lower(x1.code) like lower(concat('%', lower(:#{#form.code}), '%' ))) " +
            "   and (:#{#form.name} is null or :#{#form.name} = '' or lower(x1.name) like lower(concat('%', lower(:#{#form.name}), '%' ) )) " +
            "   and (:#{#form.brand} is null or :#{#form.brand} = '' or concat('', x1.brand.id) = :#{#form.brand}) " +
            "   and (:#{#form.category} is null or :#{#form.category} = '' or concat('', x1.category.id) = :#{#form.category}) " +
            "")
    Page<Product> searchByAdmin(ProductSearchFormByAdmin form, Pageable pageable);

    @Query("select x1 from Product x1 where x1.id in :#{#ids}")
    Set<Product> findByIds(List<Long> ids);
}
