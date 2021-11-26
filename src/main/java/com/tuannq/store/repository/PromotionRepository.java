package com.tuannq.store.repository;

import com.tuannq.store.entity.Promotion;
import com.tuannq.store.model.request.PromotionAdminSearchForm;
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
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query(" SELECT x1 FROM Promotion x1 " +
            " WHERE x1.startDate < current_time " +
            "   and x1.endDate > current_time " +
            "   and x1.isDeleted = false " +
            "")
    Promotion checkHasPublicPromotion();

    @Query(nativeQuery = true, value = "SELECT * FROM promotion " +
            "WHERE coupon_code LIKE CONCAT('%',:code,'%') AND name LIKE CONCAT('%',:name,'%') " +
            "AND is_public LIKE CONCAT('%',:ispublic,'%') AND is_active LIKE CONCAT('%',:active,'%')")
    Page<Promotion> adminGetListPromotion(@Param("code") String code, @Param("name") String name, @Param("ispublic") String ispublic, Pageable pageable);

    public Optional<Promotion> findByCouponCode(String code);

    @Query(nativeQuery = true, value = "SELECT count(id) FROM orders WHERE JSON_UNQUOTE(JSON_EXTRACT(promotion, '$.couponCode')) = ?1")
    public int checkPromotionInUse(String code);

    @Query(nativeQuery = true, value = "SELECT * FROM promotion WHERE expired_at > now() AND is_active = true")
    public List<Promotion> getAllValidPromotion();

    @Query("select x1 from Promotion x1 " +
            " where (:#{#form.code} is null or :#{#form.code} = '' or lower(x1.couponCode) like lower(concat('%', coalesce(:#{#form.code}, ''), '%'))) " +
            "   and (:#{#form.name} is null or :#{#form.name} = '' or lower(x1.name) like lower(concat('%', coalesce(:#{#form.name}, ''), '%'))) " +
            "   and (:#{#form.discountType} is null or x1.discountType = :#{#form.discountType} ) " +
            "   and (:#{#form.startDate} is null or x1.startDate > :#{#form.startDate}  or x1.startDate = :#{#form.startDate} ) " +
            "   and (:#{#form.endDate} is null or x1.endDate > :#{#form.endDate}  or x1.endDate = :#{#form.endDate} ) " +
            "   and (:#{#form.isPublic} is null or x1.isPublic = :#{#form.isPublic} ) " +
            "")
    Page<Promotion> searchByAdmin(PromotionAdminSearchForm form, Pageable pageable);


    @Query(nativeQuery = true,
            value = " select distinct x1.id, " +
                    "       x1.coupon_code, " +
                    "       x1.created_at, " +
                    "       x1.discount_type, " +
                    "       x1.discount_value, " +
                    "       x1.is_public, " +
                    "       x1.maximum_discount_value, " +
                    "       x1.name, " +
                    "       x1.is_deleted, " +
                    "       x1.modified_at, " +
                    "       x1.quantity, " +
                    "       x1.created_by, " +
                    "       x1.modified_by, " +
                    "       x1.end_date, " +
                    "       x1.start_date " +
                    " from promotion x1 " +
                    "          left join product_promotion pp on x1.id = pp.promotion_id " +
                    "          left join product x3 on pp.product_id = x3.id " +
                    " where x1.is_deleted = 0 " +
                    "   and x1.quantity > (select count(*) " +
                    "                      from orders x2 " +
                    "                      where JSON_EXTRACT(x2.promotion, '$.couponCode') = :#{#couponCode}) " +
                    "   and x1.coupon_code = :#{#couponCode}  " +
                    "   and x1.end_date > now() " +
                    "   and x1.start_date < now() " +
                    "   and (x3.id in :#{#productIds} or x3.id is null ) " +
                    "  ")
    Promotion checkCouponCode(String couponCode, Set<Long> productIds);
}
