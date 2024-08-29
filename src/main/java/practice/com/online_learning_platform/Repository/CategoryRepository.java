package practice.com.online_learning_platform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.com.online_learning_platform.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
