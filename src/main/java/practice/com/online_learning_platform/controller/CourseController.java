package practice.com.online_learning_platform.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.com.online_learning_platform.dto.request.RegisterCourseDto;
import practice.com.online_learning_platform.dto.response.ResponseMessageDto;
import practice.com.online_learning_platform.entity.Course;
import practice.com.online_learning_platform.entity.Student;
import practice.com.online_learning_platform.service.CourseService;

@Tag(name = "Course")
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController{

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<ResponseMessageDto> create(@Valid @RequestBody RegisterCourseDto createCourseDto) {

       Course course =  courseService.create(createCourseDto);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(ResponseMessageDto
                        .builder()
                        .status(HttpStatus.OK.value())
                        .message("Course adding successfully with id - " + course.getId())
                        .build());
    }
    public void enrollment(Student student) {

    }
}
