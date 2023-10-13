package Gameatorium.videogames.models;

import lombok.*;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity
@Table(name= "user_role")
public class UsersRole {

    @Id
    private int userId;

    @Column(name = "role_type", nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "role_table_id")
    private Users user;
}
