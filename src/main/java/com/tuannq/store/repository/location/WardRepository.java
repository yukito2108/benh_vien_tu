package com.tuannq.store.repository.location;

import com.tuannq.store.entity.location.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, String> {
    List<Ward> findByDistrictId(String districtId);

    @Query("select concat(:#{#street},', ', x1.type, ' ', x1.name, ', ', x2.type, ' ', x2.name, ', ', x3.type, ' ', x3.name ) from Ward x1 " +
            " join District x2 on x1.districtId = x2.districtId " +
            " join Province x3 on x2.provinceId = x3.provinceId " +
            " where x1.wardId = :#{#wardId} ")
    String getAddress(String wardId, String street);
}
