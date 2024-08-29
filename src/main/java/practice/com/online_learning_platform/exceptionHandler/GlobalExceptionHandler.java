package practice.com.online_learning_platform.exceptionHandler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import practice.com.online_learning_platform.dto.response.ResponseMessageDto;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> methodArgumentNotValidException(MethodArgumentNotValidException exc) {

        List<String> messages = getErrorMessage(exc);

        Map<String, Object> errors = new LinkedHashMap<>();
        errors.put("status" , HttpStatus.BAD_REQUEST.value());
        errors.put("message" ,  messages);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    private List<String> getErrorMessage(MethodArgumentNotValidException exc) {
         return exc.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessageDto> customExceptionHandler(CustomGlobalException exc) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessageDto
                        .builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message(exc.getMessage())
                        .build()
                );
    }

}
