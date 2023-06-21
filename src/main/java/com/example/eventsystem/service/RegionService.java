package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.RegionDTO;
import com.example.eventsystem.model.Country;
import com.example.eventsystem.model.Region;
import com.example.eventsystem.repository.CountryRepository;
import com.example.eventsystem.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  10:08   *  tedaSystem
 */
@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    private final CountryRepository countryRepository;


    public ApiResponse<List<Region>> getAll(Long countryId) {
        List<Region> regions;
        if (countryId == null) {
            regions = regionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        } else {
            regions = regionRepository.findAllByCountry_Id(countryId, Sort.by(Sort.Direction.ASC, "country_id"));
        }
        return ApiResponse.<List<Region>>builder().
                success(true).
                status(200).
                data(regions).
                message("Here !").
                build();
    }


    public ApiResponse<Region> getOne(Long id) {
        Optional<Region> regionOptional = regionRepository.findById(id);
        if (regionOptional.isEmpty()) {
            return ApiResponse.<Region>builder().
                    message("Region is not found !").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<Region>builder().
                message("Here !").
                status(200).
                success(true).
                data(regionOptional.get()).
                build();
    }

    public ApiResponse<?> add(RegionDTO regionDTO) {
        Optional<Country> countryOptional = countryRepository.findById(regionDTO.getCountryId());
        if (countryOptional.isEmpty()) {
            return ApiResponse.builder().
                    message("Region is not found !").
                    success(false).
                    status(400).
                    build();

        }
        Region region = new Region();
        region.setName(regionDTO.getName());
        region.setDescription(regionDTO.getDescription());
        region.setCountry(countryOptional.get());
        regionRepository.save(region);
        return ApiResponse.builder().
                message("Region is created !").
                success(true).
                status(201).
                build();
    }

    public ApiResponse<?> edit(Long id, RegionDTO dto) {
        Optional<Region> regionOptional = regionRepository.findById(id);
        Optional<Country> countryOptional = countryRepository.findById(dto.getCountryId());
        if (regionOptional.isEmpty() || countryOptional.isEmpty()) {
            return ApiResponse.builder().
                    message("Region is not found !").
                    success(false).
                    status(400).
                    build();
        }
        Region region = regionOptional.get();
        region.setName(dto.getName());
        region.setDescription(dto.getDescription());
        region.setCountry(countryOptional.get());
        regionRepository.save(region);
        return ApiResponse.builder().
                message("Region is edited !").
                success(true).
                status(205).
                build();
    }

    public ApiResponse<?> delete(Long id) {
        Optional<Region> regionOptional = regionRepository.findById(id);
        if (regionOptional.isEmpty()) {
            return ApiResponse.builder().
                    message("Region is not found !").
                    success(false).
                    status(400).
                    build();
        }
        regionRepository.deleteById(id);
        return ApiResponse.builder().
                message("Is deleted !").
                success(true).
                status(200).
                build();
    }

}
