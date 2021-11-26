package com.tuannq.store.service.impl;

import com.tuannq.store.entity.location.District;
import com.tuannq.store.entity.location.Province;
import com.tuannq.store.entity.location.Ward;
import com.tuannq.store.repository.location.DistrictRepository;
import com.tuannq.store.repository.location.ProvinceRepository;
import com.tuannq.store.repository.location.WardRepository;
import com.tuannq.store.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationServiceImpl implements LocationService {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;

    @Autowired
    public LocationServiceImpl(ProvinceRepository provinceRepository, DistrictRepository districtRepository, WardRepository wardRepository) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    public List<Ward> findWardByDistrictId(String districtId) {
        return wardRepository.findByDistrictId(districtId);
    }

    @Override
    public List<District> findDistrictByProvinceId(String provinceId) {
        return districtRepository.findByProvinceId(provinceId);
    }

    @Override
    public List<Province> findProvince() {
        return provinceRepository.findAll();
    }

    @Override
    public String getAddress(String wardId, String street) {
        return wardRepository.getAddress(wardId, street);
    }
}
