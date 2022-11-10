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
@Table(name = "IMAGES_VIEW")
public class ImagesView {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "IMAGE", nullable = false)
    private byte[] image;

    @Column(name = "\"TYPE\"", nullable = false, length = 50)
    private String type;

    protected ImagesView() {
    }
}