package practice.com.online_learning_platform.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.com.online_learning_platform.Repository.InstructorRepository;
import practice.com.online_learning_platform.Repository.StudentRepository;
import practice.com.online_learning_platform.Repository.UserRepository;
import practice.com.online_learning_platform.dto.response.UserDto;
import practice.com.online_learning_platform.entity.Instructor;
import practice.com.online_learning_platform.entity.Student;
import practice.com.online_learning_platform.entity.User;
import practice.com.online_learning_platform.exceptionHandler.CustomGlobalException;
import practice.com.online_learning_platform.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    private final UserMapper userMapper;


    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll() ;
        return users
                .stream()
                .map(userMapper::mapUserToUserDto)
                .toList();
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomGlobalException("User not found")
        );
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
