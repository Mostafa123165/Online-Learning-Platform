package practice.com.online_learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import practice.com.online_learning_platform.dto.request.RegisterCourseDto;
import practice.com.online_learning_platform.entity.Category;
import practice.com.online_learning_platform.entity.Course;
import practice.com.online_learning_platform.entity.Instructor;
import practice.com.online_learning_platform.entity.Tag;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RegisterCourseMapper {

    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "title" , source = "registerCourseDto.title")
    @Mapping(target = "description" , source = "registerCourseDto.description")
    @Mapping(target = "status", expression = "java(practice.com.online_learning_platform.utility.enums.CourseStatus.Pending_Approval)")
    @Mapping(target = "courseType" , source = "registerCourseDto.courseType")
    Course mapToCourseFromRegisterCourse(RegisterCourseDto registerCourseDto, Set<Tag> tags, Instructor instructor, Category category);

}
