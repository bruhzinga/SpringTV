package by.zvor.springtv.Entity;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Mapping for DB view
 */
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllGenres",
                procedureName = "getAllGenres", resultClasses = GenresView.class, parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "result", type = void.class)
        })})


@Getter
@Entity
@Immutable
@Table(name = "GENRES_VIEW")
public class GenresView {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    protected GenresView() {
    }
}