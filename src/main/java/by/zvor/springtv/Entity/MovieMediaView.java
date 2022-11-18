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
@Setter
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

    public MovieMediaView() {
    }
}