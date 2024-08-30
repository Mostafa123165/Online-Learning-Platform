package practice.com.online_learning_platform.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import practice.com.online_learning_platform.Repository.CategoryRepository;
import practice.com.online_learning_platform.Repository.CourseRepository;
import practice.com.online_learning_platform.Repository.StudentRepository;
import practice.com.online_learning_platform.Repository.TagRepository;
import practice.com.online_learning_platform.dto.request.RegisterCourseDto;
import practice.com.online_learning_platform.dto.request.TagRequestDto;
import practice.com.online_learning_platform.entity.*;
import practice.com.online_learning_platform.exceptionHandler.CustomGlobalException;
import practice.com.online_learning_platform.mapper.RegisterCourseMapper;
import practice.com.online_learning_platform.utility.enums.CourseStatus;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserManagementService userManagementService;
    private final CategoryService categoryServiceEnum;
    private final StudentRepository studentRepository;


    private final RegisterCourseMapper registerCourseMapper;

    @Transactional
    public Course create(@Valid @RequestBody RegisterCourseDto registerCourseDto) {

        validateUniqueTitle(registerCourseDto.getTitle());

        Category category =  categoryServiceEnum.findCategoryById(registerCourseDto.getCategoryId());

        Instructor instructor = userManagementService.findInstructorById(registerCourseDto.getInstructorId());

        Set<Tag> tags = getTags(registerCourseDto);

        Course course = registerCourseMapper.mapToCourseFromRegisterCourse(registerCourseDto, tags, instructor,category);

        courseRepository.save(course);

        return course;
    }

    private void validateUniqueTitle(String title) {
        if (courseRepository.existsByTitle(title)) {
            throw new CustomGlobalException("Title already exists");
        }
    }

    private Set<Tag> getTags(RegisterCourseDto registerCourseDto) {

        Set<Tag> tags = new HashSet<>();

        for (TagRequestDto tagRequestDto : registerCourseDto.getTagRequestDto()) {
            Tag tag = tagRepository
                    .findByName(tagRequestDto.getName())
                    .orElse(new Tag(tagRequestDto.getName()));
            tags.add(tag);
        }
        return tags;
    }

    @Transactional
    public void enrollUserInCourse(Long courseId, Long studentId) {

        Course course = findCourseById(courseId);
        userManagementService.findStudentById(studentId);

        if(studentRepository.existsByStudentIdAndCourseId(studentId, courseId) == 1) {
            throw new CustomGlobalException("Enrollment failed: The student already enrolled in this course");
        }

        if(course.getStatus() == CourseStatus.Approved) {
            studentRepository.enrollStudentInCourse(studentId, courseId);
        }
        else {
            throw new CustomGlobalException("Enrollment failed: The course has not been approved.");
        }

    }

    public Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(
                () ->  new CustomGlobalException("The course with ID [" + courseId + "] does not exist.")
        );
    }
}
