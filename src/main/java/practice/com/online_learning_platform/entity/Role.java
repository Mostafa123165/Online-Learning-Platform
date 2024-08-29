package practice.com.online_learning_platform.entity;

import jakarta.persistence.*;
import lombok.*;
import practice.com.online_learning_platform.utility.enums.RoleEnum;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

}
