package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.PaymentDTO;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Order;
import com.example.eventsystem.model.Payment;
import com.example.eventsystem.model.enums.PaymentType;
import com.example.eventsystem.repository.OrderRepository;
import com.example.eventsystem.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Mansurov Abdusamad  *  13.12.2022  *  15:43   *  tedaSystem
 */
@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${page.size}")
    private int size;

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    public ApiResponse<Page<Payment>> getAll(int page, Employee employee) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> paymentPage = paymentRepository.findAllByReceiver_Company_Id(employee.getCompany().getId(), pageable);

        if (paymentPage.isEmpty()) {
            return ApiResponse.<Page<Payment>>builder().
                    message("Payments not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        return ApiResponse.<Page<Payment>>builder().
                message("Payments here!!!").
                success(true).
                status(200).
                data(paymentPage).
                build();
    }

    public ApiResponse<Payment> getOne(Long id, Employee employee) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isEmpty() || paymentOptional.get().getReceiver().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Payment>builder().
                    message("Payment not found!!!").
                    success(false).
                    status(400).
                    build();
        }

        return ApiResponse.<Payment>builder().
                message("Payment here!!!").
                success(true).
                status(200).
                data(paymentOptional.get()).
                build();
    }


    public ApiResponse<Payment> add(PaymentDTO dto, Employee receiver) {
        Optional<Order> orderOptional = orderRepository.findById(dto.getOrderId());
        if (orderOptional.isEmpty() || orderOptional.get().getReceiver().getCompany().getId().equals(receiver.getCompany().getId())) {
            return ApiResponse.<Payment>builder().
                    message("Order not found!!!").
                    success(false).
                    status(400).
                    build();
        }

        Payment payment = new Payment();
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setPaymentNumber(dto.getPaymentNumber());
        payment.setPrice(dto.getPrice());
        payment.setPaymentType(PaymentType.valueOf(dto.getPaymentType()));
        payment.setReceiver(receiver);

        Payment save = paymentRepository.save(payment);

        return ApiResponse.<Payment>builder().
                message("Payment saved!!!").
                success(true).
                status(201).
                data(save).
                build();
    }

    public ApiResponse<?> delete(Long id, Employee employee) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);

        if (paymentOptional.isEmpty() || !paymentOptional.get().getReceiver().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.builder().
                    message("Payment not found!!!").
                    success(false).
                    status(400).
                    build();
        }

        Payment payment = paymentOptional.get();

        if (!payment.isActive()) {
            return ApiResponse.builder().
                    message("Payment is not active!!!").
                    success(false).
                    status(400).
                    build();
        }

        payment.setActive(false);
        paymentRepository.save(payment);
        return ApiResponse.builder().
                message("Payment is deleted!!!").
                success(true).
                status(200).
                build();
    }
}
