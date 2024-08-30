package practice.com.online_learning_platform.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import practice.com.online_learning_platform.dto.response.UserDto;
import practice.com.online_learning_platform.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "username" , expression = "java(user.getFirstName() + user.getLastName())")
    UserDto mapUserToUserDto(User user);
}
