package practice.com.online_learning_platform.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import practice.com.online_learning_platform.Repository.*;
import practice.com.online_learning_platform.dto.request.RegistrationRequestDto;
import practice.com.online_learning_platform.entity.*;
import practice.com.online_learning_platform.exceptionHandler.CustomGlobalException;
import practice.com.online_learning_platform.mapper.UserRegistrationMapper;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserManagementService {

    final private UserRepository userRepository;
    final private StudentRepository studentRepository;
    final private AdminRepository adminRepository;
    final private InstructorRepository instructorRepository;
    final private RoleService roleService;

    final private UserRegistrationMapper userRegistrationMapper;

    @Transactional
    public User registerUser(@Valid RegistrationRequestDto userRegistrationRequestDto) {

        // ensure that the email is provided is unique
        if(userRepository.existsByEmail(userRegistrationRequestDto.getEmail()))
            throw new CustomGlobalException("Email address is already in use");

        User user = userRegistrationMapper
                .mapToUserFromUserRegistrationRequestDto(userRegistrationRequestDto);

        Set<Role> roles = getRoles(userRegistrationRequestDto);

        user.setRole(roles);
        userRepository.save(user);


        for (Role role : roles) {
            switch (role.getName()) {
                case STUDENT:
                    Student student = Student.builder().user(user).build();
                    student.setUser(user);
                    studentRepository.save(student);
                    break;
                case INSTRUCTOR:
                    Instructor instructor = Instructor.builder().user(user).build();
                    instructor.setUser(user);
                    instructorRepository.save(instructor);
                    break;
                case ADMIN:
                    Admin admin = Admin.builder().user(user).build();
                    admin.setUser(user);
                    adminRepository.save(admin);
                    break;
            }
        }

        return user;

    }

    private Set<Role> getRoles(RegistrationRequestDto userRegistrationRequestDto) {

        return userRegistrationRequestDto.getRoles().stream().map(
                role -> {
                    Role roleEntity = roleService.findByName(role) ;
                    if(roleEntity == null)
                        throw new CustomGlobalException("The specified role is not found - " + role.toString());
                    else
                        return roleEntity;
                }
        ).collect(Collectors.toSet());

    }

    public Instructor findInstructorById(Long id) {

        return instructorRepository.findById(id).orElse(null);
    }


}
