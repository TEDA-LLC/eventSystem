package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.CallDTO;
import com.example.eventsystem.model.*;
import com.example.eventsystem.repository.CallRepository;
import com.example.eventsystem.repository.RequestRepository;
import com.example.eventsystem.repository.ReviewCategoryRepository;
import com.example.eventsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mansurov Abdusamad  *  01.12.2022  *  13:47   *  tedaSystem
 */
@Service
@RequiredArgsConstructor
public class CallService {
    @Value("${page.size}")
    private int size;

    private final CallRepository callRepository;
    private final UserRepository userRepository;
    private final ReviewCategoryRepository ReviewCategoryRepository;
    private final RequestRepository requestRepository;


    public ApiResponse<Page<Call>> getAll(int page, Employee employee) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Call> calls = callRepository.findAllByClient_Department_Company_Id(employee.getCompany().getId(), pageable);
        if (calls.isEmpty()) {
            return ApiResponse.<Page<Call>>builder().
                    message("Calls not found").
                    status(400).
                    success(false).
                    build();
        }

        return ApiResponse.<Page<Call>>builder().
                message("Calls here").
                status(200).
                success(true).
                data(calls).
                build();
    }


    public ApiResponse<Call> getOne(Long id, Employee employee) {
        Optional<Call> callOptional = callRepository.findById(id);

        if (callOptional.isPresent() && callOptional.get().getClient().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Call>builder().
                    message("Call here").
                    status(200).
                    success(true).
                    data(callOptional.get()).
                    build();
        }
        return ApiResponse.<Call>builder().
                message("Calls here").
                status(200).
                success(true).
                build();
    }

    public ApiResponse<Page<Call>> getByUser(Long id, int page, LocalDate startDate, LocalDate endDate, Employee employee) {
        Pageable pageable = PageRequest.of(page, size);
        Long companyId = employee.getCompany().getId();
        Page<Call> calls = callRepository.findAllByEmployee_IdAndClient_Department_Company_Id(id, companyId, pageable);

        if (startDate != null && endDate == null) {
            LocalDateTime startDataTime = LocalDateTime.of(startDate, LocalTime.now());
            calls = callRepository.findAllByEmployee_IdAndClient_Department_Company_IdAndCreatedTimeAfter(id, companyId, startDataTime, pageable);
        }
        if (endDate != null && startDate == null) {
            LocalDateTime endDataTime = LocalDateTime.of(endDate, LocalTime.now());
            calls = callRepository.findAllByEmployee_IdAndClient_Department_Company_IdAndCreatedTimeBefore(id, companyId, endDataTime, pageable);
        }
        if (endDate != null && startDate != null) {
            LocalDateTime startDataTime = LocalDateTime.of(startDate, LocalTime.now());
            LocalDateTime endDataTime = LocalDateTime.of(endDate, LocalTime.now());
            calls = callRepository.findAllByEmployee_IdAndClient_Department_Company_IdAndCreatedTimeBetween(id, companyId, startDataTime, endDataTime, pageable);
        }

        if (calls.isEmpty()) {
            ApiResponse.<Page<Call>>builder().
                    message("Calls not found").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<Page<Call>>builder().
                message("Calls here").
                status(200).
                success(true).
                data(calls).
                build();
    }

    public ApiResponse<Call> add(CallDTO dto, Employee employee) {

        Optional<User> clientOptional = userRepository.findById(dto.getClientId());

        if (clientOptional.isEmpty() || !clientOptional.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Call>builder().
                    message("Client not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        Call call = new Call();
        User client = clientOptional.get();
        if (dto.getRequestId() != null) {
            Optional<Request> requestOptional = requestRepository.findById(dto.getRequestId());
            if (requestOptional.isEmpty() || !requestOptional.get().getUser().getId().equals(client.getId())) {
                return ApiResponse.<Call>builder().
                        message("Request not found!!!").
                        status(400).
                        success(false).
                        build();
            }
            call.setRequest(requestOptional.get());
        }

        List<ReviewCategory> reviewCategoryList = new ArrayList<>();

        for (Long ReviewCategoryId : dto.getReviewCategoryIds()) {
            Optional<ReviewCategory> ReviewCategoryOptional = ReviewCategoryRepository.findById(ReviewCategoryId);
            if (ReviewCategoryOptional.isEmpty()) {
                return ApiResponse.<Call>builder().
                        message("ReviewCategory not found!!!").
                        status(400).
                        success(false).
                        build();
            }

            ReviewCategory reviewCategory = ReviewCategoryOptional.get();

            if (!reviewCategory.isActive()) {
                return ApiResponse.<Call>builder().
                        message(reviewCategory.getName() + " not active!!!").
                        status(400).
                        success(false).
                        build();
            }
            reviewCategoryList.add(reviewCategory);
        }

        call.setClient(client);
        call.setReviewCategories(reviewCategoryList);
        call.setEmployee(employee);
        call.setDescription(dto.getDescription());
        call.setSuccess(dto.isSuccess());
        if (dto.getNextConnectionDate() != null) {
            call.setNextConnectionDate(dto.getNextConnectionDate());
        }
        Call save = callRepository.save(call);

        return ApiResponse.<Call>builder().
                message("Call saved!!!").
                status(201).
                success(true).
                data(save).
                build();
    }

    public ApiResponse<Call> edit(Long id, CallDTO dto, Employee employee) {
        Optional<Call> callOptional = callRepository.findById(id);
        if (callOptional.isEmpty()) {
            return ApiResponse.<Call>builder().
                    message("Call not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        Call call = callOptional.get();
        if (!(employee.getSelectedRole().toString().equals("ADMIN") || employee.getSelectedRole().toString().equals("DIRECTOR")) && !Objects.equals(call.getEmployee().getId(), employee.getId())) {
            return ApiResponse.<Call>builder().
                    message(" You don't have permission for this operation!!!").
                    status(400).
                    success(false).
                    build();
        }
        Optional<User> clientOptional = userRepository.findById(dto.getClientId());
        if (clientOptional.isEmpty()) {
            return ApiResponse.<Call>builder().
                    message("Client not found!!!").
                    status(400).
                    success(false).
                    build();
        }

        if (!call.getCreatedTime().toLocalDate().equals(LocalDate.now())) {
            return ApiResponse.<Call>builder().
                    message("Time has passed. You can't change it now!!!").
                    status(400).
                    success(false).
                    build();
        }


        User client = clientOptional.get();
        List<ReviewCategory> reviewCategoryList = call.getReviewCategories();
        for (Long ReviewCategoryId : dto.getReviewCategoryIds()) {
            Optional<ReviewCategory> ReviewCategoryOptional = ReviewCategoryRepository.findById(ReviewCategoryId);
            if (ReviewCategoryOptional.isEmpty()) {
                return ApiResponse.<Call>builder().
                        message("ReviewCategory not found!!!").
                        status(400).
                        success(false).
                        build();
            }

            ReviewCategory ReviewCategory = ReviewCategoryOptional.get();

            if (!ReviewCategory.isActive()) {
                return ApiResponse.<Call>builder().
                        message(ReviewCategory.getName() + " not active!!!").
                        status(400).
                        success(false).
                        build();
            }

            reviewCategoryList.add(ReviewCategory);
        }
        call.setClient(client);
        call.setReviewCategories(reviewCategoryList);
        call.setEmployee(employee);
        call.setDescription(dto.getDescription());
        call.setSuccess(dto.isSuccess());
        if (dto.getNextConnectionDate() != null) {
            call.setNextConnectionDate(dto.getNextConnectionDate());
        }
        Call save = callRepository.save(call);

        return ApiResponse.<Call>builder().
                message("Call edited!!!").
                status(200).
                success(true).
                data(save).
                build();
    }

    public ApiResponse<?> delete(Long id, Employee employee) {
        Optional<Call> callOptional = callRepository.findById(id);
        if (callOptional.isEmpty()) {
            return ApiResponse.<Call>builder().
                    message("Call not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        Call call = callOptional.get();
        if (!call.getCreatedTime().toLocalDate().equals(LocalDate.now()) && !Objects.equals(call.getEmployee().getId(), employee.getId()) && employee.getSelectedRole().toString().equals("OPERATOR")) {
            return ApiResponse.<Call>builder().
                    message("Time has passed. You can't change it now!!!").
                    status(400).
                    success(false).
                    build();
        }

        if (!(employee.getSelectedRole().toString().equals("ADMIN") || employee.getSelectedRole().toString().equals("DIRECTOR")) && !Objects.equals(call.getEmployee().getId(), employee.getId())) {
            return ApiResponse.<Call>builder().
                    message(" You don't have permission for this operation!!!").
                    status(400).
                    success(false).
                    build();
        }

        callRepository.delete(call);
        return ApiResponse.<Call>builder().
                message("Call deleted!!!").
                status(200).
                success(true).
                build();
    }

    public ApiResponse<Page<Call>> getByClient(Long clientId, int page, Employee employee) {
        Optional<User> clientOptional = userRepository.findById(clientId);
        if (clientOptional.isEmpty() || !clientOptional.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Page<Call>>builder().
                    message("Client not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Call> callPage = callRepository.findAllByClient_Id(clientId, pageable);
        if (callPage.isEmpty()) {
            return ApiResponse.<Page<Call>>builder().
                    message("No call list found for this client").
                    success(false).
                    status(400).
                    build();
        }
        return ApiResponse.<Page<Call>>builder().
                message("OK!").
                success(true).
                status(200).
                data(callPage).
                build();
    }
}
