package practice.com.online_learning_platform.dto.response;

import lombok.Getter;
import lombok.Setter;
import practice.com.online_learning_platform.entity.Role;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private Set<Role> role ;

    private String phoneNumber;

    private String address;

    private LocalDateTime registrationDate;
}
