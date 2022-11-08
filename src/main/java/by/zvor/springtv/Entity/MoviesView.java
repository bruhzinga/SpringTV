package by.zvor.springtv.Entity;

import lombok.Getter;
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