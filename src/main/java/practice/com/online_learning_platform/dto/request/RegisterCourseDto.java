package practice.com.online_learning_platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import practice.com.online_learning_platform.utility.enums.CourseType;

import java.util.Set;

@Getter
@Setter
public class RegisterCourseDto {

    @NotBlank(message = "title must not be empty.")
    private String title;

    @NotBlank(message = "description must not be empty.")
    private String description;

    @NotNull(message = "category id must not be empty.")
    private Long categoryId;

    @NotNull(message = "Course type must be specified as 'either' FREE or 'PAID'.")
    private CourseType courseType;

    @NotNull(message = "instructor id must not be empty.")
    private Long instructorId ;

    private Set<TagRequestDto> tagRequestDto;
}
