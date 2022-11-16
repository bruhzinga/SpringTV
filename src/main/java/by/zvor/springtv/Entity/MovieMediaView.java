package by.zvor.springtv.Entity;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Mapping for DB view
 */
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "getMovieByIdWithMedia",
                procedureName = "getMovieByIdWithMedia",
                resultClasses = MovieMediaView.class, parameters = {
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "result", type = void.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "movieId", type = Integer.class)
        })})

@Getter
@Entity
@Immutable
@Table(name = "MOVIE_MEDIA_VIEW")
public class MovieMediaView {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "IMAGE_NAME", nullable = false, length = 50)
    private String imageName;

    @Column(name = "IMAGE", nullable = false)
    private byte[] image;

    @Column(name = "VIDEO_NAME", nullable = false, length = 50)
    private String videoName;

    @Column(name = "VIDEO", nullable = false)
    private byte[] video;

    protected MovieMediaView() {
    }
}