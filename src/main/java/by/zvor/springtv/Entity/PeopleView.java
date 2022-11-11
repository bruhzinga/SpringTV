package by.zvor.springtv.Entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * Mapping for DB view
 */
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "findAllByProfession",
                procedureName = "findAllByProfession", resultClasses = PeopleView.class, parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "ProfessionIn", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "result", type = void.class),
        })})


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

    protected PeopleView() {
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