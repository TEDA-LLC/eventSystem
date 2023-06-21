package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.AvatarDTO;
import com.example.eventsystem.model.Attachment;
import com.example.eventsystem.model.Avatar;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.User;
import com.example.eventsystem.repository.AvatarRepository;
import com.example.eventsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Mansurov Abdusamad  *  07.12.2022  *  12:11   *  tedaSystem
 */
@Service
@RequiredArgsConstructor
public class AvatarService {
    @Value("${page.size}")
    private int size;

    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;

    public ApiResponse<Page<Avatar>> getAll(int page, Employee employee) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Avatar> avatarPage = avatarRepository.findAllByUser_Department_Company_Id(employee.getCompany().getId(), pageable);

        if (avatarPage.isEmpty()) {
            return ApiResponse.<Page<Avatar>>builder().
                    message("Avatars not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        return ApiResponse.<Page<Avatar>>builder().
                message("Avatars here!!!").
                success(true).
                status(200).
                data(avatarPage).
                build();
    }

    public ApiResponse<Avatar> getOne(Long id, Employee employee) {
        Optional<Avatar> avatarOptional = avatarRepository.findById(id);
        if (avatarOptional.isEmpty() || !avatarOptional.get().getUser().getDepartment().getCompany().getId().equals(employee.getCompany().getId()))
            return ApiResponse.<Avatar>builder().
                    message("Avatar not found!").
                    success(false).
                    status(400).
                    build();

        return ApiResponse.<Avatar>builder().
                message("Avatar here!").
                success(true).
                status(200).
                data(avatarOptional.get()).
                build();

    }

    @SneakyThrows
    public ApiResponse<Avatar> add(AvatarDTO dto, Employee employee) {
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        if (userOptional.isEmpty() || !userOptional.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId()))
            return ApiResponse.<Avatar>builder().
                    message("User not found!").
                    success(false).
                    status(400).
                    build();
        Avatar avatar = new Avatar();
        if (dto.getPhotos() != null) {
            List<Attachment> attachmentList = new ArrayList<>();
            for (MultipartFile photo : dto.getPhotos()) {
                Attachment attachment = new Attachment();
                attachment.setOriginalName(photo.getOriginalFilename());
                attachment.setBytes(photo.getBytes());
                attachment.setContentType(photo.getContentType());
                attachment.setSize(photo.getSize());
                attachmentList.add(attachment);
            }
            avatar.setPhotos(attachmentList);
        }
        avatar.setUser(userOptional.get());
        avatar.setHobby(dto.getHobby());
        avatar.setPersonal(dto.getPersonal());
        avatar.setAboutWork(dto.getAboutWork());

        Avatar save = avatarRepository.save(avatar);

        return ApiResponse.<Avatar>builder().
                message("Avatar saved!").
                success(true).
                status(201).
                data(save).
                build();
    }

    @SneakyThrows
    public ApiResponse<Avatar> edit(Long id, AvatarDTO dto, Employee employee) {
        Optional<Avatar> avatarOptional = avatarRepository.findById(id);
        if (avatarOptional.isEmpty()|| !avatarOptional.get().getUser().getDepartment().getCompany().getId().equals(employee.getCompany().getId()))
            return ApiResponse.<Avatar>builder().
                    message("Avatar not found!").
                    success(false).
                    status(400).
                    build();
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        if (userOptional.isEmpty())
            return ApiResponse.<Avatar>builder().
                    message("User not found!").
                    success(false).
                    status(400).
                    build();
        Avatar avatar = avatarOptional.get();

        if (dto.getPhotos() != null) {
            List<Attachment> oldPhotos = avatar.getPhotos();
            List<MultipartFile> newPhotos = dto.getPhotos();
            int i = 0;
            for (; i < oldPhotos.size(); i++) {
                Attachment attachment = oldPhotos.get(i);
                if (i >= newPhotos.size()) {
                    oldPhotos.set(i, null);
                    continue;
                }
                MultipartFile multipartFile = newPhotos.get(i);
                attachment.setOriginalName(multipartFile.getOriginalFilename());
                attachment.setBytes(multipartFile.getBytes());
                attachment.setSize(multipartFile.getSize());
                attachment.setContentType(multipartFile.getContentType());
            }
            for (; i < newPhotos.size(); i++) {
                Attachment attachment = new Attachment();
                MultipartFile multipartFile = newPhotos.get(i);
                attachment.setContentType(multipartFile.getContentType());
                attachment.setBytes(multipartFile.getBytes());
                attachment.setSize(multipartFile.getSize());
                attachment.setOriginalName(multipartFile.getOriginalFilename());
                oldPhotos.add(attachment);
            }
            oldPhotos.removeIf(Objects::isNull);
            avatar.setPhotos(oldPhotos);
        }
        avatar.setUser(userOptional.get());
        avatar.setHobby(dto.getHobby());
        avatar.setPersonal(dto.getPersonal());
        avatar.setAboutWork(dto.getAboutWork());

        Avatar save = avatarRepository.save(avatar);

        return ApiResponse.<Avatar>builder().
                message("Avatar edited!").
                success(true).
                status(200).
                data(save).
                build();
    }
}



