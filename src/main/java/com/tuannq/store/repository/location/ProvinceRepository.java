package com.tuannq.store.repository.location;

import com.tuannq.store.entity.location.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {
    Province findByProvinceId(String provinceId);
}
