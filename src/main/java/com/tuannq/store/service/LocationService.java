package com.tuannq.store.service;

import com.tuannq.store.entity.location.District;
import com.tuannq.store.entity.location.Province;
import com.tuannq.store.entity.location.Ward;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {
    List<Ward> findWardByDistrictId(String districtId);

    List<District> findDistrictByProvinceId(String provinceId);

    List<Province> findProvince();

    String getAddress(String wardId, String street);
}
