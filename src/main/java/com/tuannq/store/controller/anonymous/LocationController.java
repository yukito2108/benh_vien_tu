package com.tuannq.store.controller.anonymous;

import com.tuannq.store.entity.location.District;
import com.tuannq.store.entity.location.Province;
import com.tuannq.store.entity.location.Ward;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/province")
    public ResponseEntity<SuccessResponse<List<Province>>> findProvince() {
        var data = locationService.findProvince();
        return ResponseEntity.ok(new SuccessResponse<>(data));
    }

    @GetMapping("/ward/{districtId}")
    public ResponseEntity<SuccessResponse<List<Ward>>> findWardByDistrictId(@PathVariable String districtId) {
        var data = locationService.findWardByDistrictId(districtId);
        return ResponseEntity.ok(new SuccessResponse<>(data));
    }

    @GetMapping("/district/{provinceId}")
    public ResponseEntity<SuccessResponse<List<District>>> findDistrictByProvinceId(@PathVariable String provinceId) {
        var data = locationService.findDistrictByProvinceId(provinceId);
        return ResponseEntity.ok(new SuccessResponse<>(data));
    }

}
