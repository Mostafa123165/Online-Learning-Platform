package practice.com.online_learning_platform.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

        // Check if the email is already in use
        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new CustomGlobalException("Email address '" + userRegistrationRequestDto.getEmail() + "' is already in use");
        }

        User user = userRegistrationMapper.mapToUserFromUserRegistrationRequestDto(userRegistrationRequestDto);

        Set<Role> roles = getRoles(userRegistrationRequestDto);

        user.setRole(roles);

        userRepository.save(user);

        assignUserTypeBasedOnRole(roles, user);

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

    private void assignUserTypeBasedOnRole(Set<Role> roles, User user) {
        for (Role role : roles) {
            switch (role.getName()) {
                case STUDENT:
                    assignStudentBasedOnRole(user);
                    break;
                case INSTRUCTOR:
                    assignInstructorBasedOnRole(user);
                    break;
                case ADMIN:
                    assignAdminBasedOnRole(user);
                    break;
                default:
                    throw new CustomGlobalException("The specified role is not found - " + role);
            }
        }
    }

    private void assignStudentBasedOnRole(User user) {
        Student student = Student.builder().user(user).build();
        studentRepository.save(student);
    }

    private void assignInstructorBasedOnRole(User user) {
        Instructor instructor = Instructor.builder().user(user).build();
        instructorRepository.save(instructor);
    }

    private void assignAdminBasedOnRole(User user) {
        Admin admin = Admin.builder().user(user).build();
        adminRepository.save(admin);
    }

    public Instructor findInstructorById(Long id) {
        return instructorRepository.findById(id).orElseThrow(
                () ->  new CustomGlobalException(
                        "The Instructor with ID [" +
                                id +
                                "] does not exist. Please provide a valid instructor.")
        );
    }


    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () ->  new CustomGlobalException(
                        "The Student with ID [" +
                                id +
                                "] does not exist. Please provide a valid student.")
        );
    }
}
