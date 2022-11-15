package by.zvor.springtv.Entity;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "getAllMoviesWithoutMedia",
                procedureName = "getAllMoviesWithoutMedia",
                resultClasses = MoviesView.class, parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "result", type = void.class)
        }),
        @NamedStoredProcedureQuery(
                name = "getMovieByIdWithoutMedia",
                procedureName = "getMovieByIdWithoutMedia",
                resultClasses = MoviesView.class, parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "movieId", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "result", type = void.class)
        })
})

/**
 * Mapping for DB view
 */
@Getter
@Entity
@Immutable
@Table(name = "MOVIES_VIEW")
public class MoviesView {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @Column(name = "YEAR", nullable = false)
    private Short year;

    @Column(name = "DESCRIPTION", nullable = false, length = 500)
    private String description;

    @Column(name = "NUMBER_OF_VIEWS", nullable = false)
    private Integer numberOfViews;

    @Column(name = "DIRECTOR", nullable = false, length = 50)
    private String director;

    @Column(name = "GENRE", nullable = false, length = 50)
    private String genre;

    protected MoviesView() {
    }

}