package com.example.eventsystem.service;

import com.example.eventsystem.dto.*;
import com.example.eventsystem.model.*;
import com.example.eventsystem.model.enums.RoleType;
import com.example.eventsystem.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Malikov Azizjon    ourSystem    26.12.2022    15:55
 */

@Service
@RequiredArgsConstructor
public class CompanyService {

    @Value("${page.size}")
    private int size;

    private final CompanyRepository companyRepository;
    private final DistrictRepository districtRepository;

    private final AttachmentRepository attachmentRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final BankInfoRepository bankInfoRepository;

    public ApiResponse<Page<Company>> getAll(int page, Employee employee, Boolean active) {

        Pageable pageable = PageRequest.of(page, size);
        if (employee == null)
            return ApiResponse.<Page<Company>>builder().
                    message("Company not found!!!").
                    status(400).
                    success(false).
                    build();

        Page<Company> companies = companyRepository.findAllByActive(pageable, employee.getCompany(), active);
        if (companies.isEmpty()) {
            return ApiResponse.<Page<Company>>builder().
                    success(false).
                    status(400).
                    message("Companies not found").
                    build();
        }
        return ApiResponse.<Page<Company>>builder().
                success(true).
                status(200).
                message("Companies here").
                data(companies).
                build();
    }

    public ApiResponse<Company> getOne(Long id, Employee employee) {
        if (employee == null)
            return ApiResponse.<Company>builder().
                    message("Company not found!!!").
                    status(400).
                    success(false).
                    build();

        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent() && companyOptional.get().getId().equals(employee.getCompany().getId())) {
            Company company = companyOptional.get();
            if (company.equals(employee.getCompany()))
                return ApiResponse.<Company>builder().
                        success(true).
                        status(200).
                        message("Company here").
                        data(companyOptional.get()).
                        build();
        }
        return ApiResponse.<Company>builder().
                success(false).
                status(400).
                message("Company is not found").
                build();
    }

    @SneakyThrows
    public ApiResponse<?> add(CompanyDTO companyDTO) {
        Company company = new Company();
        if (companyDTO.getAttachment() != null) {
            MultipartFile photo = companyDTO.getAttachment();
            Attachment attachment = new Attachment();
            attachment.setBytes(photo.getBytes());
            attachment.setOriginalName(photo.getOriginalFilename());
            attachment.setContentType(photo.getContentType());
            attachment.setSize(photo.getSize());
            company.setAttachment(attachment);
        }
        company.setName(companyDTO.getName());
        company.setActivityType(companyDTO.getActiveType());
        company.setStirNumber(companyDTO.getStirNumber());
        if (companyDTO.getMemberOrganizationId() != null) {
            Optional<Company> companyOptional = companyRepository.findById(companyDTO.getMemberOrganizationId());
            if (companyOptional.isEmpty()){
                return ApiResponse.<Company>builder().
                        message("Member company not found!!!").
                        status(400).
                        success(false).
                        build();
            }
        }
        Address address = new Address();
        AddressDTO addressDTO = companyDTO.getAddressDTO();
        Optional<District> districtOptional = districtRepository.findById(addressDTO.getDistrictId());
        if (districtOptional.isEmpty()) {
            return ApiResponse.<Company>builder().
                    message("District not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        address.setDistrict(districtOptional.get());
        address.setStreetHome(address.getStreetHome());
        company.setAddress(address);
        Company saveCompany = companyRepository.save(company);
        if (companyDTO.getDirector() != null) {
//            dto.getDirector().setCompanyId(save.getId());
//            ApiResponse<Employee> response = employeeService.add(dto.getDirector());
//            if (!response.isSuccess()) {
//                return response;
//            }
//            company = save;
//            company.setDirector(response.getData());
            EmployeeDTO dto = companyDTO.getDirector();
            if (employeeRepository.findByUsername(dto.getUsername()).isPresent()) {
                return ApiResponse.<Employee>builder().
                        message("This username " + dto.getUsername() + " is used. Please enter another phone number").
                        success(false).
                        status(400).
                        build();
            }

            Employee employee = new Employee();
            if (dto.getAddressDTO() != null) {
                AddressDTO userAddressDTO = dto.getAddressDTO();
                Address userAddress = new Address();

                Optional<District> districtOptional1 = districtRepository.findById(userAddressDTO.getDistrictId());
                if (districtOptional1.isEmpty()) {
                    return ApiResponse.<Employee>builder().
                            message("District not found!!!").
                            success(false).
                            status(400).
                            build();
                }
                userAddress.setDistrict(districtOptional1.get());
                userAddress.setStreetHome(address.getStreetHome());
                employee.setAddress(address);
            }
            employee.setCompany(saveCompany);
            employee.setFullName(dto.getFullName());
            employee.setPhoneFirst(dto.getPhoneFirst());
            employee.setPhoneSecond(dto.getPhoneSecond());
            employee.setPassword(passwordEncoder.encode(dto.getPassword()));

            Set<RoleType> roles = new HashSet<>();

            for (String roleString : dto.getRoleList()) {
                roles.add(RoleType.valueOf(roleString));
            }

            employee.setRoles(roles);
            Employee save = employeeRepository.save(employee);
        } else {
            Optional<Employee> employeeOptional = employeeRepository.findById(companyDTO.getDirectorId());
            if (employeeOptional.isEmpty()) {
                return ApiResponse.<Company>builder().
                        message("Director not found!!!").
                        status(400).
                        success(false).
                        build();
            }
            company.setDirector(employeeOptional.get());
        }
        if (companyDTO.getBankInfoDTO() != null && !companyDTO.getBankInfoDTO().isEmpty()){
            List<BankInfoDTO> bankInfoDTO = companyDTO.getBankInfoDTO();
            for (BankInfoDTO infoDTO : bankInfoDTO) {
                BankInfo bankInfo = new BankInfo();
                bankInfo.setMfo(infoDTO.getMfo());
                bankInfo.setBranch(infoDTO.getBranch());
                bankInfo.setCurrency(infoDTO.getCurrency());
                bankInfo.setAccountNumber(infoDTO.getAccountNumber());
                bankInfo.setCompany(saveCompany);
                bankInfoRepository.save(bankInfo);
            }
        }
        return ApiResponse.builder().
                message("Company is created").
                status(201).
                success(true).
                data(companyRepository.save(company)).
                build();
    }

    @SneakyThrows
    public ApiResponse<?> edit(Long id, CompanyDTO dto, Employee employee) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            return ApiResponse.builder().
                    success(false).
                    status(400).
                    message("Company is not found").
                    build();
        }
        Company company = companyOptional.get();
        if (!company.getId().equals(employee.getCompany().getId())) {
            return ApiResponse.builder().
                    success(false).
                    status(400).
                    message("Company is not found").
                    build();
        }
        if (dto.getAttachment() != null && !dto.getAttachment().isEmpty()) {
            MultipartFile photo = dto.getAttachment();
            Attachment attachment = new Attachment();
            if (company.getAttachment() != null)
                attachment = company.getAttachment();
            attachment.setBytes(photo.getBytes());
            attachment.setOriginalName(photo.getOriginalFilename());
            attachment.setContentType(photo.getContentType());
            attachment.setSize(photo.getSize());
            company.setAttachment(attachment);
        }
        company.setName(dto.getName());
        company.setActivityType(dto.getActiveType());
        company.setStirNumber(dto.getStirNumber());
        Optional<Company> memberCompanyOptional = companyRepository.findById(dto.getMemberOrganizationId());
        if (memberCompanyOptional.isEmpty() || memberCompanyOptional.get().getId().equals(company.getId())) {
            return ApiResponse.builder().
                    message("Member organization not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        company.setMemberOrganization(memberCompanyOptional.get());

        Address address = company.getAddress();
        AddressDTO addressDTO = dto.getAddressDTO();
        Optional<District> districtOptional = districtRepository.findById(addressDTO.getDistrictId());

        if (districtOptional.isEmpty()) {
            return ApiResponse.<Company>builder().
                    message("District not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        address.setDistrict(districtOptional.get());
        address.setStreetHome(address.getStreetHome());
        company.setAddress(address);
        if (company.getBankInfo()!=null){
            bankInfoRepository.deleteAll(company.getBankInfo());
        }
        if (dto.getBankInfoDTO() != null && !dto.getBankInfoDTO().isEmpty()){
            List<BankInfoDTO> bankInfoDTO = dto.getBankInfoDTO();
            for (BankInfoDTO infoDTO : bankInfoDTO) {
                BankInfo bankInfo = new BankInfo();
                bankInfo.setMfo(infoDTO.getMfo());
                bankInfo.setBranch(infoDTO.getBranch());
                bankInfo.setCurrency(infoDTO.getCurrency());
                bankInfo.setAccountNumber(infoDTO.getAccountNumber());
                bankInfo.setCompany(company);
                bankInfoRepository.save(bankInfo);
            }
        }
        return ApiResponse.builder().
                message("Company is edited!").
                status(200).
                success(true).
                data(companyRepository.save(company)).
                build();
    }

    public ApiResponse<?> delete(Long id, Employee employee) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            return ApiResponse.builder().
                    message("Company is not found !").
                    success(false).
                    status(400).
                    build();
        }

        Company company = companyOptional.get();
        if (company.getId() != employee.getCompany().getId()) {
            return ApiResponse.builder().
                    success(false).
                    status(400).
                    message("Company is not found").
                    build();
        }
        company.setActive(!company.isActive());
        companyRepository.save(company);
        return ApiResponse.builder().
                message("Is deleted !").
                success(true).
                status(200).
                build();
    }

    public ApiResponse<List<Company>> getAllList() {
        List<Company> companies = companyRepository.findAll();

        if (companies.isEmpty()) {
            return ApiResponse.<List<Company>>builder().
                    message("Users are not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<List<Company>>builder().
                message("Users here!!!").
                status(200).
                success(true).
                data(companies).
                build();
    }

    public ResponseEntity<?> getPhoto(Long id) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        if (attachmentOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Photo not found!!!");
        }
        Attachment attachment = attachmentOptional.get();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(attachment.getContentType()))
                .contentLength(attachment.getSize())
                .body(attachment.getBytes());
    }

    @SneakyThrows
    public ApiResponse<?> editPhoto(Long id, MultipartFile photo) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        if (attachmentOptional.isEmpty()){
            return ApiResponse.builder().
                    message("Photo not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        Attachment attachment = attachmentOptional.get();
        attachment.setBytes(photo.getBytes());
        attachment.setOriginalName(photo.getOriginalFilename());
        attachment.setContentType(photo.getContentType());
        attachment.setSize(photo.getSize());
        attachmentRepository.save(attachment);
        return ApiResponse.builder().
                message("Photo edited!!!").
                status(200).
                success(true).
                build();
    }

}
