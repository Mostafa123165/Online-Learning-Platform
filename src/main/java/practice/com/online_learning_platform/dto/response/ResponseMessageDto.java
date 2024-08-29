package practice.com.online_learning_platform.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class ResponseMessageDto {
    private int status;
    private String message;
}
