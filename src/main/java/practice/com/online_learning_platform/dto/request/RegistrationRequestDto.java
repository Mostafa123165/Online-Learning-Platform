package practice.com.online_learning_platform.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import practice.com.online_learning_platform.utility.enums.RoleEnum;

import java.util.Set;

@Getter
@Setter
public class RegistrationRequestDto {

    @NotBlank(message = "FirstName must not be empty.")
    private String firstName;

    @NotBlank(message = "LastName must not be empty.")
    private String lastName;

    @Email(message = "Please provide a valid email address.")
    @NotBlank(message = "Email must not be empty.")
    private String email;

    @NotBlank(message = "Password must not be empty.")
    @Size(min = 8 , message = "Password must be at least 8 characters.")
    private String password;

    @Size(min = 11, max = 11, message = "Phone number must be exactly 11 characters.")
    private String phoneNumber;

    @NotBlank(message = "Address must not be empty.")
    private String address;

    @NotEmpty(message = "role must be not empty")
    private Set<RoleEnum> roles;

}
