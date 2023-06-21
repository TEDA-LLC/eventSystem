package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Country;
import com.example.eventsystem.repository.CountryRepository;
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
public class CountryService {
    private final CountryRepository countryRepository;

    public ApiResponse<List<Country>> getAll() {
        List<Country> countries = countryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (countries.isEmpty()) {
            return ApiResponse.<List<Country>>builder().
                    success(false).
                    message("Empty").
                    status(400).
                    build();
        }
        return ApiResponse.<List<Country>>builder().
                message("Here !").
                data(countries).
                success(true).
                status(200).
                build();
    }

    public ApiResponse<Country> getOne(Long id) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        if (countryOptional.isEmpty()) {
            return ApiResponse.<Country>builder().
                    success(false).
                    message("Brand not found !").
                    status(409).
                    build();
        }
        return ApiResponse.<Country>builder().
                message("Here !").
                data(countryOptional.get()).
                success(true).
                status(200).
                build();
    }

    public ApiResponse<?> add(Country country) {
        if (country.getName().isEmpty() && country.getShortName().isEmpty()) {
            return ApiResponse.builder().
                    message("Name is empty !").
                    success(false).
                    status(400).
                    build();
        }

        countryRepository.save(country);
        return ApiResponse.builder().
                status(201).
                message("Country is created !").
                success(true).
                build();
    }


    public ApiResponse<?> edit(Long id, Country country) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        if (countryOptional.isEmpty()) {
            return ApiResponse.builder().
                    message("Country is not found !").
                    success(false).
                    status(400).
                    build();
        }

        Country country1 = countryOptional.get();
        country1.setName(country.getName());
        country1.setShortName(country.getShortName());
        countryRepository.save(country1);

        return ApiResponse.builder().
                status(205).
                message("Country is edited !").
                success(true).
                build();
    }


    public ApiResponse<?> deleteOne(Long id) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        if (countryOptional.isEmpty()) {
            return ApiResponse.builder().
                    message("Country is not found !").
                    success(false).
                    status(400).
                    build();
        }
        countryRepository.deleteById(id);
        return ApiResponse.builder().
                message("Is deleted !").
                success(true).
                status(200).
                build();
    }

    public ApiResponse<Country> getResident() {
        return ApiResponse.<Country>builder().
                status(200).
                message("Country here !").
                success(true).
                data(countryRepository.findByShortNameEqualsIgnoreCase("uz").get()).
                build();
    }
}
