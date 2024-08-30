package practice.com.online_learning_platform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import practice.com.online_learning_platform.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Modifying
    @Query(value = "INSERT INTO student_course (student_id, course_Id) VALUES (:studentId,:courseId)", nativeQuery = true)
    void enrollStudentInCourse(@Param("studentId") Long studentId,@Param("courseId") Long courseId);

    @Query(value = "SELECT COUNT(*) > 0 " +
                   "FROM student_course " +
                   "WHERE student_id = :studentId AND course_id = :courseId "
            ,nativeQuery = true)
    int existsByStudentIdAndCourseId(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId);

}
