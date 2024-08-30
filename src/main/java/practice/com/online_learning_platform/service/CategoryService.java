package practice.com.online_learning_platform.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.com.online_learning_platform.Repository.CategoryRepository;
import practice.com.online_learning_platform.dto.request.CategoryRequestDto;
import practice.com.online_learning_platform.entity.Category;
import practice.com.online_learning_platform.exceptionHandler.CustomGlobalException;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findCategoryById(Long id) {

        return categoryRepository.findById(id).orElseThrow(
                () -> new CustomGlobalException(
                        "The category with ID [" +
                                id +
                                "] does not exist. Please provide a valid category.")
        );
    }

    @Transactional
    public Category saveCategory(@Valid CategoryRequestDto categoryRequestDto) {

        if(categoryRepository.existsByName(categoryRequestDto.getName()))
            throw new CustomGlobalException("Category name already exist");

        Category category = Category
                .builder()
                .name(categoryRequestDto.getName())
                .build();

        return categoryRepository.save(category);
    }
}
