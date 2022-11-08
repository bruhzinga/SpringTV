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
@Table(name = "COMMENTS_VIEW")
public class CommentsView {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "\"COMMENT\"", nullable = false, length = 50)
    private String comment;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    protected CommentsView() {
    }

    public Integer getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }
}