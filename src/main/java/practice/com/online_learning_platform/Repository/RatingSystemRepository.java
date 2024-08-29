package practice.com.online_learning_platform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.com.online_learning_platform.entity.RatingSystem;

@Repository
public interface RatingSystemRepository extends JpaRepository<RatingSystem, Long> {
}
