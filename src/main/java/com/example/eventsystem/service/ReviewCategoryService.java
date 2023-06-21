package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.ReviewCategory;
import com.example.eventsystem.repository.ReviewCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  13:48   *  tedaSystem
 */
@Service
@RequiredArgsConstructor
public class ReviewCategoryService {

    private final ReviewCategoryRepository reviewCategoryRepository;

    public ApiResponse<List<ReviewCategory>> getAll() {
        List<ReviewCategory> reviewCategory = reviewCategoryRepository.findAllByActiveTrue();
        if (reviewCategory.isEmpty()) {
            return ApiResponse.<List<ReviewCategory>>builder().
                    message("Review category not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<List<ReviewCategory>>builder().
                message("Here!!!").
                status(200).
                success(true).
                data(reviewCategory).
                build();
    }

    public ApiResponse<ReviewCategory> getOne(Long id) {
        Optional<ReviewCategory> reviewCategoryOptional = reviewCategoryRepository.findById(id);
        if (reviewCategoryOptional.isEmpty()) {
            return ApiResponse.<ReviewCategory>builder().
                    message("ReviewCategory not found").
                    status(400).
                    success(false).
                    build();
        }
        ReviewCategory reviewCategory = reviewCategoryOptional.get();
        if (!reviewCategory.isActive()) {
            return ApiResponse.<ReviewCategory>builder().
                    message("This ReviewCategory is inactive!!!").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<ReviewCategory>builder().
                message("OK!!!").
                status(200).
                success(true).
                data(reviewCategory).
                build();
    }

    public ApiResponse<ReviewCategory> add(String name) {
        if (name == null || name.length() == 0)
            return ApiResponse.<ReviewCategory>builder().
                    message("This name is not supported!!!").
                    status(400).
                    success(false).
                    build();
        Optional<ReviewCategory> ReviewCategoryOptional = reviewCategoryRepository.findByName(name);
        if (ReviewCategoryOptional.isPresent()) {
            return ApiResponse.<ReviewCategory>builder().
                    message("This name is used. Please, enter another name !!!").
                    status(400).
                    success(false).
                    build();
        }

        return ApiResponse.<ReviewCategory>builder().
                message("ReviewCategory created").
                success(true).
                status(201).
                data(reviewCategoryRepository.save(ReviewCategory.builder().
                        name(name).
                        active(true).
                        build())).
                build();

    }

    public ApiResponse<ReviewCategory> edit(Long id, String name) {
        Optional<ReviewCategory> reviewCategoryOptionalByName = reviewCategoryRepository.findByName(name);
        Optional<ReviewCategory> reviewCategoryOptionalById = reviewCategoryRepository.findById(id);

        if (reviewCategoryOptionalById.isEmpty()) {
            return ApiResponse.<ReviewCategory>builder().
                    message("ReviewCategory not found").
                    status(400).
                    success(false).
                    build();
        }
        ReviewCategory reviewCategory = reviewCategoryOptionalById.get();

        if (reviewCategoryOptionalByName.isPresent()) {

            ReviewCategory reviewCategory1 = reviewCategoryOptionalByName.get();

            if (reviewCategory1 != reviewCategory && !reviewCategory1.isActive()) {
                reviewCategory1.setActive(true);
                ReviewCategory save = reviewCategoryRepository.save(reviewCategory1);
                return ApiResponse.<ReviewCategory>builder().
                        message("ReviewCategory edited").
                        status(200).
                        success(true).
                        data(save).
                        build();
            }
            return ApiResponse.<ReviewCategory>builder().
                    message("This name is used. Please, enter another name !!!").
                    status(400).
                    success(false).
                    build();
        }

        reviewCategory.setName(name);
        ReviewCategory save = reviewCategoryRepository.save(reviewCategory);
        return ApiResponse.<ReviewCategory>builder().
                message("ReviewCategory edited").
                status(200).
                success(true).
                data(save).
                build();
    }

    public ApiResponse<ReviewCategory> delete(Long id) {
        Optional<ReviewCategory> reviewCategoryOptional = reviewCategoryRepository.findById(id);
        if (reviewCategoryOptional.isEmpty()) {
            return ApiResponse.<ReviewCategory>builder().
                    message("ReviewCategory not found").
                    status(400).
                    success(false).
                    build();
        }

        ReviewCategory reviewCategory = reviewCategoryOptional.get();

        reviewCategory.setActive(false);

        reviewCategoryRepository.save(reviewCategory);
        return ApiResponse.<ReviewCategory>builder().
                message("ReviewCategory deleted").
                status(200).
                success(true).
                build();
    }
}
