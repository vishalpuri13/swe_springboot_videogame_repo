package Gameatorium.videogames.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "games", uniqueConstraints = { @UniqueConstraint(columnNames = { "game_name", "version","platform" }) })
public class Games {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameId;

    @Column(name = "game_name", nullable = false)
    private String gameName;

    @Column(name = "version")
    private String version;

    @Column(name = "genre", nullable=false)
    private String genre;

    @Column(name = "platform", nullable=false)
    private String platform;

    @Column(name = "release_date", nullable = false)
//    @Temporal(TemporalType.DATE)
    private LocalDate releaseDate;

}
