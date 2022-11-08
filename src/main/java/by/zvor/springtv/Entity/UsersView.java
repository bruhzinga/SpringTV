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
@Table(name = "USERS_VIEW")
public class UsersView {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    @Column(name = "PASSWORD_HASH", nullable = false, length = 50)
    private String passwordHash;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "ROLE", nullable = false, length = 50)
    private String role;

    protected UsersView() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}