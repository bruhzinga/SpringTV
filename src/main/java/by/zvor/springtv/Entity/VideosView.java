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
@Table(name = "VIDEOS_VIEW")
public class VideosView {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "VIDEO", nullable = false)
    private byte[] video;

    @Column(name = "\"TYPE\"", nullable = false, length = 50)
    private String type;

    protected VideosView() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getVideo() {
        return video;
    }

    public String getType() {
        return type;
    }
}