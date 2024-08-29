package practice.com.online_learning_platform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.com.online_learning_platform.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
