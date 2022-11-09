package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.UsersView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersViewRepository extends JpaRepository<UsersView, Integer> {

    UsersView getUserById(long id);

    @Query(value = "SELECT USERNAME FROM USERS_VIEW WHERE ID = :userId", nativeQuery = true)
    String getUsernameFromUserId(Long userId);


    @Query(value = "SELECT ID FROM USERS WHERE USERNAME = :username", nativeQuery = true)
    Long GetUserIdByUsername(@Param("username") String login);


    @Procedure(procedureName = "REGISTER_USER")
    void saveUser(@Param("user_login") String login, @Param("user_password") String password, @Param("user_email") String email, @Param("user_role_id") Long id);

    @Query(value = "SELECT ID FROM USERS WHERE USERNAME = :login AND PASSWORD_HASH=:password_hash ", nativeQuery = true)
    Long GetUserIdByUsernameAndPassword(String login, String password_hash);

    @Query(value = "SELECT PASSWORD_HASH FROM USERS WHERE USERNAME = :login", nativeQuery = true)
    String GetUserEncryptedPasswordByLogin(String login);

}