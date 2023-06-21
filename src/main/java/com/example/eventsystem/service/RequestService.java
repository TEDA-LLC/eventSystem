package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.RequestDTO;
import com.example.eventsystem.model.*;
import com.example.eventsystem.model.enums.RegisteredType;
import com.example.eventsystem.model.enums.RequestType;
import com.example.eventsystem.repository.ProductRepository;
import com.example.eventsystem.repository.RequestRepository;
import com.example.eventsystem.repository.SiteHistoryRepository;
import com.example.eventsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {
    @Value("${page.size}")
    private int size;
    private final RequestRepository requestRepository;
    private final SiteHistoryRepository siteHistoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final TelegramService telegramService;

    public ApiResponse<Page<Request>> getRequest(int page, Boolean view, Employee employee) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Request> requests;

        if (view == null)
            requests = requestRepository.findAll(pageable);
        else
            requests = requestRepository.findAllByViewAndUser_Department_Company_Id(view, employee.getCompany().getId(),pageable);

        if (requests.isEmpty()) {
            return ApiResponse.<Page<Request>>builder().
                    message("Requests not found !").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<Page<Request>>builder().
                message("Requests here !").
                status(200).
                success(true).
                data(requests).
                build();
    }

    public ApiResponse<Request> getOneRequest(Long id, Employee employee) {
        Optional<Request> requestOptional = requestRepository.findById(id);

        if (requestOptional.isEmpty() || !requestOptional.get().getUser().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Request>builder().
                    message("Requests not found !").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<Request>builder().
                message("Requests here !").
                status(200).
                success(true).
                data(requestOptional.get()).
                build();
    }

    public ApiResponse<?> edit(String qrcode, Employee employee) {
        if (employee.getProduct() == null) {
            return ApiResponse.builder()
                    .message("You haven't related event!!!")
                    .status(400)
                    .success(false)
                    .build();
        }
        Optional<Product> productOptional = productRepository.findById(employee.getProduct().getId());
        if (productOptional.isEmpty() || !productOptional.get().getCategory().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.builder()
                    .message("Event not found ")
                    .status(400)
                    .success(false)
                    .build();
        }
        Optional<User> userOptional = userRepository.findByQrcode(UUID.fromString(qrcode));
        if (userOptional.isEmpty()) {
            return ApiResponse.builder()
                    .message("User not found ")
                    .status(400)
                    .success(false)
                    .build();
        }
        List<Request> requestList = requestRepository.findAllByProductAndUser(productOptional.get(), userOptional.get());
        if (requestList.isEmpty()) {
            return ApiResponse.builder()
                    .message("User is not registered")
                    .status(400)
                    .success(false)
                    .build();
        }
        Request request = requestList.get(0);
        if (request.getArrivalTime() != null) {
            return ApiResponse.builder()
                    .message("User is registered")
                    .status(200)
                    .success(true)
                    .data(request.getUser())
                    .build();
        }
        request.setArrivalTime(LocalDateTime.now());
        request.setView(true);
        requestRepository.save(request);
        return ApiResponse.builder()
                .message("Success ")
                .status(200)
                .success(true)
                .data(request.getUser())
                .build();
    }

    public ApiResponse<Page<SiteHistory>> getSiteHistory(int page) {

        Pageable pageable = PageRequest.of(page, size);

        Page<SiteHistory> siteHistories = siteHistoryRepository.findAll(pageable);
        if (siteHistories.isEmpty()) {
            return ApiResponse.<Page<SiteHistory>>builder().
                    message("History not found !").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<Page<SiteHistory>>builder().
                message("History here !").
                status(200).
                success(true).
                data(siteHistories).
                build();
    }


    public ApiResponse<SiteHistory> getOneSiteHistory(Long id) {
        Optional<SiteHistory> historyOptional = siteHistoryRepository.findById(id);

        if (historyOptional.isEmpty()) {
            return ApiResponse.<SiteHistory>builder().
                    message("History not found !").
                    status(400).
                    success(false).
                    build();
        }

        return ApiResponse.<SiteHistory>builder().
                message("History not found !").
                status(400).
                success(false).
                data(historyOptional.get()).
                build();
    }

    public ApiResponse<Request> addRequest(RequestDTO dto, Employee employee) {
        Optional<User> userOptional = userRepository.findById(dto.getUserId());

        if (userOptional.isEmpty() || !userOptional.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Request>builder().
                    message("User not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        Request request = new Request();
        Optional<Product> productOptional = productRepository.findById(dto.getProductId());
        productOptional.ifPresent(request::setProduct);

        request.setRequestStatusType(RequestType.UNDER_REVIEW);
        request.setUser(userOptional.get());
        request.setAboutProduct(dto.getAboutProduct());
        request.setCategory(dto.getCategory());
        request.setAgree(dto.isAgree());
        Request save = requestRepository.save(request);

        return ApiResponse.<Request>builder().
                message("Request accepted!!!").
                success(true).
                status(201).
                data(save).
                build();
    }

    @SneakyThrows
    public ApiResponse<Request> editRequestStatus(Long id, Employee employee) {
        Optional<Request> requestOptional = requestRepository.findById(id);
        if (requestOptional.isEmpty() || !requestOptional.get().getUser().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Request>builder().
                    message("Request id not found !").
                    status(400).
                    success(false).
                    build();
        }
        Request request = requestOptional.get();
        request.setView(true);
        request.setEmployee(employee);
        if (request.getUser().getChatId() != null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Sizning " + request.getId() + " raqamli murojatingizni " + request.getEmployee().getFullName() + " qabul qildi.");
            sendMessage.setChatId(request.getUser().getChatId());
            telegramService.execute(sendMessage);
        }
        request.setArrivalTime(LocalDateTime.now());
        requestRepository.save(request);
        return ApiResponse.<Request>builder().
                message("Sent!!!").
                success(true).
                status(200).
                build();

    }
    public ApiResponse<Request> add(Long userId, Long eventId, Employee employee) {
        Optional<Product> productOptional = productRepository.findById(eventId);
        if (productOptional.isEmpty() || !productOptional.get().getCategory().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Request>builder().
                    message("Event not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        Product product = productOptional.get();
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty() || !userOptional.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Request>builder().
                    message("User not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        User user = userOptional.get();
        List<Request> requestList = requestRepository.findAllByProductAndUser(product, user);
        if (!requestList.isEmpty()) {
            return ApiResponse.<Request>builder().
                    message("This user is already registered!!!").
                    status(200).
                    success(true).
                    data(requestList.get(0)).
                    build();
        }
        Request request = new Request();
        request.setProduct(product);
        request.setUser(user);
        request.setRegisteredType(RegisteredType.CALL_CENTER);
        Request save = requestRepository.save(request);
        return ApiResponse.<Request>builder().
                message("Registered!!!").
                status(201).
                success(true).
                data(save).
                build();
    }
}
