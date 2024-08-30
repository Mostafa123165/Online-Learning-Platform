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
import practice.com.online_learning_platform.dto.request.CategoryRequestDto;
import practice.com.online_learning_platform.dto.response.ResponseMessageDto;
import practice.com.online_learning_platform.entity.Category;
import practice.com.online_learning_platform.service.CategoryService;

@Tag(name = "Category-api")
@RestController(value = "Category")
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryServiceEnum;


    @PostMapping
    public ResponseEntity<ResponseMessageDto> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) {
        Category category = categoryServiceEnum.saveCategory(categoryRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseMessageDto
                        .builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Created Category successfully with id " + category.getId())
                        .build());
    }
}
