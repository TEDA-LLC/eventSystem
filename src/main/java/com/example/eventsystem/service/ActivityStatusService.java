package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.ActivityStatus;
import com.example.eventsystem.model.User;
import com.example.eventsystem.model.enums.ActiveType;
import com.example.eventsystem.repository.ActivityStatusRepository;
import com.example.eventsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Mansurov Abdusamad  *  15.12.2022  *  10:49   *  tedaSystem
 */
@Service
@RequiredArgsConstructor
public class ActivityStatusService {
    @Value("${page.size}")
    private int size;
    private final ActivityStatusRepository activityStatusRepository;
    private final UserRepository userRepository;

    public ApiResponse<Page<ActivityStatus>> getAll(int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ActivityStatus> activityStatusPage = activityStatusRepository.findAll(pageable);
        if (activityStatusPage.isEmpty())
            return ApiResponse.<Page<ActivityStatus>>builder().
                    message("Not found!!!").
                    success(false).
                    status(400).
                    build();
        return ApiResponse.<Page<ActivityStatus>>builder().
                message("Here!!!").
                success(true).
                status(200).
                data(activityStatusPage).
                build();
    }

    public ApiResponse<ActivityStatus> getOne(Long id) {
        Optional<ActivityStatus> statusOptional = activityStatusRepository.findById(id);

        if (statusOptional.isEmpty()) {
            return ApiResponse.<ActivityStatus>builder().
                    message("Not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        return ApiResponse.<ActivityStatus>builder().
                message("Here!!!").
                success(true).
                status(200).
                data(statusOptional.get()).
                build();

    }

    public ApiResponse<ActivityStatus> add(String activeType, Long id) {
        Optional<User> clientOptional = userRepository.findById(id);

        if (clientOptional.isEmpty()) {
            return ApiResponse.<ActivityStatus>builder().
                    message("Client not found found!!!").
                    success(false).
                    status(400).
                    build();
        }

        ActivityStatus activityStatus = new ActivityStatus();

        Optional<ActivityStatus> lastActivityOptional = activityStatusRepository.getLastActivity(id);

        if (lastActivityOptional.isPresent()){
            ActivityStatus lastActivity = lastActivityOptional.get();
            activityStatus.setFirstCase(lastActivity.getSecondCase());
        }

        activityStatus.setSecondCase(ActiveType.valueOf(activeType));

        activityStatus.setClient(clientOptional.get());

        ActivityStatus save = activityStatusRepository.save(activityStatus);
        return ApiResponse.<ActivityStatus>builder().
                message("OK!!!").
                success(true).
                status(201).
                data(save).
                build();
    }

    public ApiResponse<ActivityStatus> edit(Long id, String activeType, Long clientId) {
        Optional<ActivityStatus> statusOptional = activityStatusRepository.findById(id);

        if (statusOptional.isEmpty()) {
            return ApiResponse.<ActivityStatus>builder().
                    message("Not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        Optional<User> clientOptional = userRepository.findById(clientId);

        if (clientOptional.isEmpty()) {
            return ApiResponse.<ActivityStatus>builder().
                    message("Client not found found!!!").
                    success(false).
                    status(400).
                    build();
        }
        ActivityStatus activityStatus = statusOptional.get();
        activityStatus.setSecondCase(ActiveType.valueOf(activeType));
        activityStatus.setClient(clientOptional.get());
        ActivityStatus save = activityStatusRepository.save(activityStatus);

        return ApiResponse.<ActivityStatus>builder().
                message("Status is edited!!!").
                success(true).
                status(200).
                data(save).
                build();


    }

    public ApiResponse<?> delete(Long id) {
        Optional<ActivityStatus> statusOptional = activityStatusRepository.findById(id);
        if (statusOptional.isEmpty()) {
            return ApiResponse.<ActivityStatus>builder().
                    message("Not found!!!").
                    success(false).
                    status(400).
                    build();
        }

        activityStatusRepository.deleteById(id);

        return ApiResponse.builder().
                message("Status is deleted!!!").
                success(false).
                status(200).
                build();
    }

    public ApiResponse<List<ActivityStatus>> getByUser(Long id) {
        List<ActivityStatus> statusList = activityStatusRepository.getByUser(id);

        if (statusList.isEmpty())
            return ApiResponse.<List<ActivityStatus>>builder().
                    message("Not found!!!").
                    success(false).
                    status(400).
                    build();

        return ApiResponse.<List<ActivityStatus>>builder().
                message("Here!!!").
                success(true).
                status(200).
                data(statusList).
                build();
    }
}



