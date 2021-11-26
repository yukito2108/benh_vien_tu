package com.tuannq.store.repository.location;

import com.tuannq.store.entity.location.District;
import com.tuannq.store.entity.location.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, String> {
    List<District> findByProvinceId(String provinceId);
}
