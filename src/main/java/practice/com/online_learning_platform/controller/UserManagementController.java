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
import practice.com.online_learning_platform.dto.request.RegistrationRequestDto;
import practice.com.online_learning_platform.dto.response.ResponseMessageDto;
import practice.com.online_learning_platform.entity.User;
import practice.com.online_learning_platform.service.UserManagementService;

@Tag(name = "user-api")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessageDto> registration(@Valid @RequestBody RegistrationRequestDto registrationRequestDto) {
        User user = userManagementService.registerUser(registrationRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(ResponseMessageDto
                        .builder()
                        .status(HttpStatus.CREATED.value())
                        .message("User registered successfully with id - " + user.getId())
                        .build());
    }

    @PostMapping("/login")
    public void login() {

    }

}
