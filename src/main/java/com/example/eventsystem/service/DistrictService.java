package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.DistrictDTO;
import com.example.eventsystem.model.District;
import com.example.eventsystem.model.Region;
import com.example.eventsystem.repository.DistrictRepository;
import com.example.eventsystem.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  10:08   *  tedaSystem
 */
@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;

    public ApiResponse<List<District>> getAll(Long regionId) {
        List<District> districts;
        if (regionId == null) {
            districts = districtRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        } else {
            districts = districtRepository.findAllByRegion_Id(regionId, Sort.by(Sort.Direction.ASC, "region_id"));
        }

        return ApiResponse.<List<District>>builder().
                success(true).
                status(200).
                message("Here !").
                data(districts).
                build();
    }

    public ApiResponse<District> getOne(Long id) {
        Optional<District> districtOptional = districtRepository.findById(id);
        if (districtOptional.isEmpty()) {
            return ApiResponse.<District>builder().
                    success(false).
                    status(400).
                    message("District is not found !").
                    build();
        }
        return ApiResponse.<District>builder().
                success(true).
                status(200).
                message("District here !").
                data(districtOptional.get()).
                build();
    }

    public ApiResponse<?> add(DistrictDTO dto) {
        District district = new District();
        district.setName(dto.getName());
        Optional<Region> regionOptional = regionRepository.findById(dto.getRegionId());
        if (regionOptional.isEmpty()) {
            return ApiResponse.<District>builder().
                    success(false).
                    status(400).
                    message("Region is not found !").
                    build();
        }

        district.setRegion(regionOptional.get());
        districtRepository.save(district);
        return ApiResponse.<District>builder().
                success(true).
                status(200).
                message("District here !").
                build();
    }

    public ApiResponse<District> edit(Long id, DistrictDTO dto) {
        Optional<District> districtOptional = districtRepository.findById(id);
        Optional<Region> regionOptional = regionRepository.findById(dto.getRegionId());
        if (districtOptional.isEmpty() || regionOptional.isEmpty()) {
            return ApiResponse.<District>builder().
                    success(false).
                    status(400).
                    message("District is not found !").
                    build();
        }
        District district = districtOptional.get();
        district.setName(dto.getName());
        district.setRegion(regionOptional.get());
        districtRepository.save(district);
        return ApiResponse.<District>builder().
                success(true).
                status(205).
                message("District is edited !").
                build();
    }

    public ApiResponse<?> delete(Long id) {
        Optional<District> districtOptional = districtRepository.findById(id);
        if (districtOptional.isEmpty()) {
            return ApiResponse.<District>builder().
                    success(false).
                    status(400).
                    message("District is not found !").
                    build();
        }
        districtRepository.deleteById(id);
        return ApiResponse.<District>builder().
                success(true).
                status(205).
                message("District is deleted !").
                build();
    }
}
