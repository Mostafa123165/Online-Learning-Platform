package practice.com.online_learning_platform.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import practice.com.online_learning_platform.Repository.CategoryRepository;
import practice.com.online_learning_platform.Repository.CourseRepository;
import practice.com.online_learning_platform.Repository.TagRepository;
import practice.com.online_learning_platform.dto.request.RegisterCourseDto;
import practice.com.online_learning_platform.dto.request.TagRequestDto;
import practice.com.online_learning_platform.entity.Category;
import practice.com.online_learning_platform.entity.Course;
import practice.com.online_learning_platform.entity.Instructor;
import practice.com.online_learning_platform.entity.Tag;
import practice.com.online_learning_platform.exceptionHandler.CustomGlobalException;
import practice.com.online_learning_platform.mapper.RegisterCourseMapper;

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


    private final RegisterCourseMapper registerCourseMapper;

    @Transactional
    public Course create(@Valid @RequestBody RegisterCourseDto registerCourseDto) {

        // check if title is unique
       if(courseRepository.existsByTitle(registerCourseDto.getTitle()))
           throw new CustomGlobalException("Title already exists");

       // check if category existed
        Category category = categoryServiceEnum.findCategoryById(registerCourseDto.getCategoryId());
        if(category == null)
            throw new CustomGlobalException(
                    "The category with ID [" +
                    registerCourseDto.getCategoryId() +
                    "] does not exist. Please provide a valid category.");

        // check if Instructor existed
        Instructor instructor = userManagementService.findInstructorById(registerCourseDto.getInstructorId());
        if(instructor == null)
            throw new CustomGlobalException("The specified instructor does not exist. Please provide a valid instructor ID.");

        // check if tag is existed before and get it if is existed
        Set<Tag> tags = getTags(registerCourseDto);

        Course course = registerCourseMapper.mapToCourseFromRegisterCourse(registerCourseDto, tags, instructor,category);

        System.out.println(course);
        System.out.println();

        // else add new tag
        return null;
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


}
