package Gameatorium.videogames.models;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "user_age", nullable=false)
    private int age;

    @Column(name = "email_id", nullable=false, unique=true)
    private String emailId;

    @Column(name = "user_password", nullable=false)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<UsersRole> usersRoles;


}
