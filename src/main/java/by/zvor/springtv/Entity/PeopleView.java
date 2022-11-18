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
@Table(name = "PEOPLE_VIEW")
public class PeopleView {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "PHOTO_ID", nullable = false)
    private Long photoId;

    @Column(name = "PROFESSION", nullable = false, length = 50)
    private String profession;

    public PeopleView() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public String getProfession() {
        return profession;
    }
}