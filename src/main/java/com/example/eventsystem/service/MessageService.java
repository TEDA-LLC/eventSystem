package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.CustomPage;
import com.example.eventsystem.dto.MessageDTO;
import com.example.eventsystem.dto.MessageResponseDTO;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Message;
import com.example.eventsystem.model.Request;
import com.example.eventsystem.model.User;
import com.example.eventsystem.model.enums.MessageType;
import com.example.eventsystem.model.enums.SendType;
import com.example.eventsystem.repository.EmployeeRepository;
import com.example.eventsystem.repository.MessageRepository;
import com.example.eventsystem.repository.RequestRepository;
import com.example.eventsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final RequestRepository requestRepository;

    public ApiResponse<List<Message>> getAll() {
        List<Message> list = messageRepository.findAll();
        return ApiResponse.<List<Message>>builder().
                status(200).
                success(true).
                message("Here").
                data(list).
                build();
    }

    public boolean editStatus(List<Long> list) {
        try {
            for (Long aLong : list) {
                Optional<Message> byId = messageRepository.findById(aLong);
                if (byId.isPresent()) {
                    Message message = byId.get();
                    message.setSendTime(LocalDateTime.now());
                    messageRepository.save(message);
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

//    public ApiResponse<Page<Message>> getAllByEmployee(Employee employee, int page, int size) {
//        Page<Message> messages;
//        ApiResponse<Page<Message>> response = new ApiResponse<>();
//        messages = messageRepository.findAllByEmployeeAndSendTime(employee, null, PageRequest.of(page, size));
//
//        response.setMessage("Here!!!");
//        response.setStatus(200);
//        response.setSuccess(true);
//        response.setData(messages);
//        return response;
//    }

    public ApiResponse<CustomPage<MessageResponseDTO>> getAllByEmployee(Employee employee, int page, int size, String type) {
        Page<Message> messagePage;
        ApiResponse<CustomPage<MessageResponseDTO>> response = new ApiResponse<>();
        if (type != null && type.equals("")) {
            MessageType messageType;
            try {
                messageType = MessageType.valueOf(type);
            } catch (Exception e) {
                response.setMessage("This message type is false!!!");
                response.setStatus(400);
                response.setSuccess(false);
                return response;
            }
            messagePage = messageRepository.findAllByEmployeeAndSendTimeAndMessageType(employee, null, messageType, PageRequest.of(page, size));
        } else {
            messagePage = messageRepository.findAllByEmployeeAndSendTime(employee, null, PageRequest.of(page, size));
        }

        List<MessageResponseDTO> messageResponseDTOList = new ArrayList<>();
        int count = 0;
        for (Message message : messagePage.getContent()) {
            MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
            messageResponseDTO.setId(message.getId());
            messageResponseDTO.setPhone(message.getUser().getPhone());
            messageResponseDTO.setText(message.getText());
            messageResponseDTO.setMessageType(message.getMessageType());
            if (message.getText().length() > 145) {
                while (message.getText().length() > 145) {
                    MessageResponseDTO messageResponseDTO1 = new MessageResponseDTO();
                    messageResponseDTO1.setId(message.getId());
                    messageResponseDTO1.setText(message.getText().substring(0, 140));
                    messageResponseDTO1.setPhone(message.getUser().getPhone());
                    messageResponseDTO1.setMessageType(message.getMessageType());
                    message.setText(message.getText().substring(140));
                    messageResponseDTOList.add(messageResponseDTO1);
                    count++;
                }
                if (message.getText().length() > 0) {
                    MessageResponseDTO messageResponseDTO1 = new MessageResponseDTO();
                    messageResponseDTO1.setId(message.getId());
                    messageResponseDTO1.setText(message.getText().substring(0, message.getText().length()));
                    messageResponseDTO1.setPhone(message.getUser().getPhone());
                    messageResponseDTOList.add(messageResponseDTO1);
                    count++;
                }
            } else {
                messageResponseDTOList.add(messageResponseDTO);
            }
        }
        CustomPage<MessageResponseDTO> messages = new CustomPage<>(messagePage, messageResponseDTOList);

        response.setMessage("Here!!!");
        response.setStatus(200);
        response.setSuccess(true);
        response.setData(messages);
        return response;
    }
    public ApiResponse<CustomPage<MessageResponseDTO>> getAllByEmployeeAndType(Employee employee, int page, int size) {
        Page<Message> messagePage;
        ApiResponse<CustomPage<MessageResponseDTO>> response = new ApiResponse<>();
            messagePage = messageRepository.findAllByEmployeeAndSendTimeNullAndMessageTypeNotNullAndSendType(employee, SendType.SMS, PageRequest.of(page, size));



        List<MessageResponseDTO> messageResponseDTOList = new ArrayList<>();
        int count = 0;
        for (Message message : messagePage.getContent()) {
            MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
            messageResponseDTO.setId(message.getId());
            messageResponseDTO.setPhone(message.getUser().getPhone());
            messageResponseDTO.setText(message.getText());
            messageResponseDTO.setMessageType(message.getMessageType());
            if (message.getText().length() > 145) {
                while (message.getText().length() > 145) {
                    MessageResponseDTO messageResponseDTO1 = new MessageResponseDTO();
                    messageResponseDTO1.setId(message.getId());
                    messageResponseDTO1.setText(message.getText().substring(0, 140));
                    messageResponseDTO1.setPhone(message.getUser().getPhone());
                    messageResponseDTO1.setMessageType(message.getMessageType());
                    message.setText(message.getText().substring(140));
                    messageResponseDTOList.add(messageResponseDTO1);
                    count++;
                }
                if (message.getText().length() > 0) {
                    MessageResponseDTO messageResponseDTO1 = new MessageResponseDTO();
                    messageResponseDTO1.setId(message.getId());
                    messageResponseDTO1.setText(message.getText().substring(0, message.getText().length()));
                    messageResponseDTO1.setPhone(message.getUser().getPhone());
                    messageResponseDTO1.setMessageType(message.getMessageType());
                    messageResponseDTOList.add(messageResponseDTO1);
                    count++;
                }
            } else {
                messageResponseDTOList.add(messageResponseDTO);
            }
        }
        CustomPage<MessageResponseDTO> messages = new CustomPage<>(messagePage, messageResponseDTOList);

        response.setMessage("Here!!!");
        response.setStatus(200);
        response.setSuccess(true);
        response.setData(messages);
        return response;
    }


    public ApiResponse<?> add(MessageDTO dto, Employee employee) {
        Message message = new Message();

        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        if (!employeeOptional.isPresent()) {
            return ApiResponse.builder()
                    .message("Employee not found")
                    .success(false)
                    .status(404)
                    .build();
        }

        Optional<Request> request = requestRepository.findById(dto.getRequest_id());
        if (!request.isPresent()) {
            return ApiResponse.builder()
                    .message("Request not found")
                    .success(false)
                    .status(404)
                    .build();
        }

        Optional<User> userOptional = userRepository.findById(dto.getUser_id());
        if (!userOptional.isPresent()) {
            return ApiResponse.builder()
                    .message("User not found")
                    .status(404)
                    .success(false)
                    .build();
        }
        message.setRequest(request.get());
        message.setText(dto.getText());
        message.setMessageType(dto.getMessageType());
//        message.setSendTime(LocalDateTime.now());
        message.setUser(userOptional.get());
        message.setEmployee(employee);
        if (userOptional.get().getDepartment() != null && userOptional.get().getDepartment().getBot() != null)
            message.setBot(userOptional.get().getDepartment().getBot());
        Message save = messageRepository.save(message);
        if (save == null) {
            return ApiResponse.builder().
                    message("Unsaved")
                    .status(400)
                    .success(false)
                    .build();
        }
        return ApiResponse.builder().
                message("Saved").
                status(200).
                success(true).
                build();
    }
}
