package by.zvor.springtv.Entity;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Mapping for DB view
 */
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getUserFavouritesByUsername",
                procedureName = "getUserFavouritesByUsername", resultClasses = FavouritesView.class, parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "UserUsername", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "result", type = void.class)
        })})
@Getter
@Entity
@Immutable
@Table(name = "FAVOURITES_VIEW")
public class FavouritesView {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    protected FavouritesView() {
    }

}