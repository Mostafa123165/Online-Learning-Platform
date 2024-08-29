package practice.com.online_learning_platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Instructor")
public class Instructor {

    @Id
    @Column(name = "id")
    private Long id;

    @MapsId
    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "instructor")
    @ToString.Exclude
    private Set<Course> course;
}