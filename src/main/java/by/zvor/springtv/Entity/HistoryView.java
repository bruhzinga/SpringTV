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
@Table(name = "HISTORY_VIEW")
public class HistoryView {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;

    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    protected HistoryView() {
    }

}