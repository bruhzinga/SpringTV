package by.zvor.springtv.Entity;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;


@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "getUserById",
                procedureName = "getUserById",
                resultClasses = UsersView.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "userId", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "result", type = void.class),
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetUserIdByUsername",
                procedureName = "GetUserIdByUsername",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "InUserName", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "outID", type = Long.class),
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetUserEncryptedPasswordByLogin",
                procedureName = "GetUserEncryptedPasswordByLogin",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "InUserName", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "password", type = String.class),
                }

        )})

/**
 * Mapping for DB view
 */
@Getter
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

}