package by.zvor.springtv.Entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping for DB view
 */
@Entity
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

    protected MovieActorsView() {
    }

    public Long getId() {
        return id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public Long getActorId() {
        return actorId;
    }

    public String getName() {
        return name;
    }
}