package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.OrderDTO;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Order;
import com.example.eventsystem.model.Product;
import com.example.eventsystem.model.User;
import com.example.eventsystem.model.enums.OrderType;
import com.example.eventsystem.repository.EmployeeRepository;
import com.example.eventsystem.repository.OrderRepository;
import com.example.eventsystem.repository.ProductRepository;
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
 * @author Mansurov Abdusamad  *  14.12.2022  *  10:15   *  tedaSystem
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    @Value("${page.size}")
    private int size;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;

    public ApiResponse<Page<Order>> getAll(int page, Boolean active, Boolean ready, Employee employee) {
        Pageable pageable = PageRequest.of(page, size);
        Long companyId = employee.getCompany().getId();
        Page<Order> orderPage;

        if (active == null && ready == null)
            orderPage = orderRepository.findAllByClient_Department_Company_IdAndActiveFalseAndReadyFalse(companyId, pageable);
        else if (Boolean.TRUE.equals(active) && Boolean.FALSE.equals(ready))
            orderPage = orderRepository.findAllByClient_Department_Company_IdAndActiveTrueAndReadyFalse(companyId, pageable);
        else if (Boolean.TRUE.equals(ready) && Boolean.FALSE.equals(active))
            orderPage = orderRepository.findAllByClient_Department_Company_IdAndActiveFalseAndReadyTrue(companyId, pageable);
        else
            orderPage = orderRepository.findAllByClient_Department_Company_IdAndActiveTrueAndReadyTrue(companyId, pageable);

        if (orderPage.isEmpty())
            return ApiResponse.<Page<Order>>builder().
                    message("Orders not found!!!").
                    success(false).
                    status(400).
                    build();

        return ApiResponse.<Page<Order>>builder().
                message("Orders here!!!").
                success(true).
                status(200).
                data(orderPage).
                build();
    }

    public ApiResponse<Order> getOne(Long id, Employee employee) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty() || !orderOptional.get().getReceiver().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Order>builder().
                    message("Order not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        return ApiResponse.<Order>builder().
                message("Order here!!!").
                success(true).
                status(200).
                data(orderOptional.get()).
                build();
    }

    public ApiResponse<Order> add(OrderDTO dto, Employee receiver) {
        Optional<User> clientOptional = userRepository.findById(dto.getClientId());
        Optional<Product> productOptional = productRepository.findById(dto.getProductId());

        if (clientOptional.isEmpty() || !clientOptional.get().getDepartment().getCompany().getId().equals(receiver.getCompany().getId()))
            return ApiResponse.<Order>builder().
                    message("Client not found!!!").
                    success(false).
                    status(400).
                    build();

        if (productOptional.isEmpty() || !productOptional.get().getCategory().getDepartment().getCompany().getId().equals(receiver.getCompany().getId()))
            return ApiResponse.<Order>builder().
                    message("Product not found!!!").
                    success(false).
                    status(400).
                    build();

        Order order = new Order();

        order.setPrice(dto.getPrice());
        if (dto.getEmployeeId() != null) {
            Optional<Employee> employeeOptional = employeeRepository.findById(dto.getEmployeeId());
            if (employeeOptional.isEmpty() || employeeOptional.get().getCompany().getId().equals(receiver.getCompany().getId()))
                return ApiResponse.<Order>builder().
                        message("Employee not found!!!").
                        success(false).
                        status(400).
                        build();
            order.setEmployee(employeeOptional.get());
        }
        order.setClient(clientOptional.get());
        order.setDescription(dto.getDescription());
        order.setEndTime(dto.getEndTime());
        order.setStartDate(dto.getStartDate());
        order.setProduct(productOptional.get());
        order.setReceiver(receiver);

        Order save = orderRepository.save(order);

        return ApiResponse.<Order>builder().
                message("The order is accepted!!!").
                success(true).
                status(201).
                data(save).
                build();
    }

    public ApiResponse<Order> edit(Long orderId, OrderDTO dto, Employee employee) {
        Optional<User> clientOptional = userRepository.findById(dto.getClientId());
        Optional<Employee> receiverOptional = employeeRepository.findById(dto.getReceiverId());
        Optional<Product> productOptional = productRepository.findById(dto.getProductId());

        if (clientOptional.isEmpty() || !clientOptional.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId()))
            return ApiResponse.<Order>builder().
                    message("Client not found!!!").
                    success(false).
                    status(400).
                    build();

        if (productOptional.isEmpty())
            return ApiResponse.<Order>builder().
                    message("Product not found!!!").
                    success(false).
                    status(400).
                    build();

        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isEmpty() || !orderOptional.get().getReceiver().getCompany().getId().equals(employee.getCompany().getId()))
            return ApiResponse.<Order>builder().
                    message("Order not found!!!").
                    success(false).
                    status(400).
                    build();


        Order order = orderOptional.get();

        order.setPrice(dto.getPrice());
        if (dto.getEmployeeId() != null) {
            Optional<Employee> employeeOptional = employeeRepository.findById(dto.getEmployeeId());
            if (employeeOptional.isEmpty() || !employeeOptional.get().getCompany().getId().equals(employee.getCompany().getId())) {
                return ApiResponse.<Order>builder().
                        message("Employee not found!!!").
                        success(false).
                        status(400).
                        build();
            }
            order.setEmployee(employeeOptional.get());
        }
        order.setClient(clientOptional.get());
        order.setDescription(dto.getDescription());
        order.setEndTime(dto.getEndTime());
        order.setStartDate(dto.getStartDate());
        order.setProduct(productOptional.get());
        order.setReceiver(receiverOptional.get());
        order.setOrderType(OrderType.valueOf(dto.getOrderType()));
        Order save = orderRepository.save(order);

        return ApiResponse.<Order>builder().
                message("The order edited!!!").
                success(true).
                status(200).
                data(save).
                build();
    }

    public ApiResponse<List<Order>> getAllByClient(Long clientId, Employee employee) {
        Optional<User> userOptional = userRepository.findById(clientId);
        if (userOptional.isEmpty() || !userOptional.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<List<Order>>builder().
                    message("Client not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        List<Order> orderList = orderRepository.findAllByClient(userOptional.get());
        return ApiResponse.<List<Order>>builder().
                message("Here!!!").
                status(200).
                success(true).
                data(orderList).
                build();
    }

    public ApiResponse<List<Order>> getAllByEmployee(Long employeeId, Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty() || !employeeOptional.get().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<List<Order>>builder().
                    message("Employee not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        List<Order> orderList = orderRepository.findAllByEmployee(employeeOptional.get());
        return ApiResponse.<List<Order>>builder().
                message("Here!!!").
                status(200).
                success(true).
                data(orderList).
                build();
    }

    public ApiResponse<List<Order>> getAllByReceiver(Long receiverId, Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findById(receiverId);
        if (employeeOptional.isEmpty() || !employeeOptional.get().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<List<Order>>builder().
                    message("Employee not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        List<Order> orderList = orderRepository.findAllByReceiver(employeeOptional.get());
        return ApiResponse.<List<Order>>builder().
                message("Here!!!").
                status(200).
                success(true).
                data(orderList).
                build();
    }
}




