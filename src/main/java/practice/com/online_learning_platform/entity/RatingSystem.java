package practice.com.online_learning_platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rating_system")
public class RatingSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @Column(name = "content")
    private String content ;

    @Column(name = "stars")
    private int stars;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
