package by.zvor.springtv.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping for DB view
 */
@Getter
@Entity
@Setter
@Immutable
@Table(name = "MOVIE_ACTORS_VIEW")
public class MovieActorsView {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "MOVIE_ID", nullable = false)
    private Long movieId;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @Column(name = "ACTOR_ID", nullable = false)
    private Long actorId;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "IMAGE_ID", nullable = false)
    private Long imageId;

    @Column(name = "\"Role\"", length = 50)
    private String role;

    public MovieActorsView() {
    }
}