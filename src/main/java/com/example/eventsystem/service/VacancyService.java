package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.VacancyDTO;
import com.example.eventsystem.model.Department;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Vacancy;
import com.example.eventsystem.repository.BotRepository;
import com.example.eventsystem.repository.DepartmentRepository;
import com.example.eventsystem.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Malikov Azizjon  *  15.01.2023  *  21:53   *  IbratClub
 */
@Service
@RequiredArgsConstructor
public class VacancyService {
//    @Value("${company.department.id}")
//    private Long departmentId;

    private final VacancyRepository vacancyRepository;
    private final BotRepository botRepository;
    private final DepartmentRepository departmentRepository;

    public ApiResponse<List<Vacancy>> getAll(Employee employee) {
        List<Vacancy> vacancies = vacancyRepository.findAllByActiveTrueAndDepartment_Company_Id(employee.getCompany().getId());
        if (vacancies.isEmpty()) {
            return ApiResponse.<List<Vacancy>>builder().
                    message("Not Found").
                    status(400).
                    success(false).
                    build();
        } else {
            return ApiResponse.<List<Vacancy>>builder().
                    message("Here").
                    status(200).
                    success(true).
                    data(vacancies).
                    build();
        }
    }

    public ApiResponse<Vacancy> getOne(Long id, Employee employee) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        if (optionalVacancy.isEmpty() || !optionalVacancy.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Vacancy>builder().
                    message("Not Found").
                    status(400).
                    success(false).
                    build();
        } else {
            return ApiResponse.<Vacancy>builder().
                    message("Here").
                    status(200).
                    success(true).
                    data(optionalVacancy.get()).
                    build();
        }
    }

    public ApiResponse<?> add(VacancyDTO vacancyDTO, Employee employee) {

        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDTO.getName());
        vacancy.setDescription(vacancyDTO.getDescription());
        vacancy.setActive(vacancyDTO.isActive());
//        if (botId != null){
//            Optional<Bot> botOptional = botRepository.findById(botId);
//            if (botOptional.isEmpty() || !botOptional.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
//                return ApiResponse.<List<Vacancy>>builder().
//                        message("Bot not found").
//                        status(400).
//                        success(false).
//                        build();
//            }
//            vacancy.setDepartment(departmentRepository.findById(departmentId).get());
//        }
        Optional<Department> departmentOptional = departmentRepository.findById(vacancyDTO.getDepartmentId());
        if (departmentOptional.isEmpty() || !departmentOptional.get().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.builder().
                    message("Department not found").
                    status(400).
                    success(false).
                    build();
        }
        vacancy.setDepartment(departmentOptional.get());
        vacancyRepository.save(vacancy);

        return ApiResponse.builder().
                message("Saved").
                status(200).
                success(true).
                build();
    }

    @SneakyThrows
    public ApiResponse<?> edit(VacancyDTO vacancyDTO, Long id, Employee employee) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
//        Optional<Vacancy> optionalCategory = categoryRepository.findById(productDTO.getCategoryId());
        if (optionalVacancy.isEmpty() || !optionalVacancy.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.builder().
                    message("Vacancy Not Found").
                    status(400).
                    success(false).
                    build();
        }
//        if (optionalVacancy.isEmpty()) {
//            return ApiResponse.builder().
//                    message("Category Not Found").
//                    status(204).
//                    success(false).
//                    build();
//        }

//        MultipartFile photo = productDTO.getPhoto();
//        Attachment attachment = new Attachment();
//        attachment.setBytes(photo.getBytes());
//        attachment.setOriginalName(photo.getOriginalFilename());

        Vacancy vacancy = optionalVacancy.get();
        vacancy.setName(vacancyDTO.getName());
        vacancy.setDescription(vacancyDTO.getDescription());
        vacancy.setActive(vacancyDTO.isActive());
        vacancyRepository.save(vacancy);

        return ApiResponse.builder().
                message("Edited !").
                status(200).
                success(true).
                build();
    }

    public ApiResponse<?> delete(Long id, Employee employee) {
        Optional<Vacancy> optionalVacancy = vacancyRepository.findById(id);
        if (optionalVacancy.isEmpty() || optionalVacancy.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.builder().
                    message("Vacancy Not Found").
                    status(400).
                    success(false).
                    build();
        }
        Vacancy vacancy = optionalVacancy.get();
        vacancy.setActive(!vacancy.isActive());
        vacancyRepository.save(vacancy);
        return ApiResponse.builder().
                message("Deleted").
                status(201).
                success(true).
                build();
    }

}
