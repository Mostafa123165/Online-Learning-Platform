package practice.com.online_learning_platform.entity;

import jakarta.persistence.*;
import lombok.*;
import practice.com.online_learning_platform.utility.enums.CourseStatus;
import practice.com.online_learning_platform.utility.enums.CourseType;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor ;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,
                           CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "course_tag",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @ToString.Exclude
    private Set<Tag> tags;

}
