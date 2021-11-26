package com.tuannq.store.repository;

import com.tuannq.store.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    @Query("SELECT x1.link from Image x1 where x1.uploadedBy.id = :#{#userId}")
    List<String> getListImageOfUser(@Param("userId") Long userId);

    Image findByLink(String link);

//    @Query(nativeQuery = true, value = "SELECT 1 FROM post, product WHERE (post.thumbnail = ?1) OR (JSON_CONTAINS(product.onfeet_images,CONCAT('\"',?1,'\"'),'$') > 0) OR (JSON_CONTAINS(product.product_images,CONCAT('\"',?1,'\"'),'$') > 0)")
//    public Integer checkImgInUse(String link);
}
