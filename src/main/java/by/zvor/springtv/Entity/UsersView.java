package by.zvor.springtv.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;


@Getter
@Setter
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

    public UsersView() {
    }

}