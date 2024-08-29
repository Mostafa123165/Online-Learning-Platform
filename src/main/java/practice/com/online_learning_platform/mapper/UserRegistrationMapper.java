package practice.com.online_learning_platform.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import practice.com.online_learning_platform.dto.request.RegistrationRequestDto;
import practice.com.online_learning_platform.entity.User;


@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {

    @Mapping(target = "registrationDate" , expression = "java(java.time.LocalDateTime.now())")
    User mapToUserFromUserRegistrationRequestDto(RegistrationRequestDto registrationRequestDto);

}
