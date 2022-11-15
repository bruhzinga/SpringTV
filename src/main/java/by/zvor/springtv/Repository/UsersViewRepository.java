package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.UsersView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersViewRepository extends JpaRepository<UsersView, Integer> {

    @Procedure(name = "getUserById")
    UsersView getUserById(@Param("userId") Long id);


    @Procedure(name = "GetUserIdByUsername")
    Long GetUserIdByUsername(@Param("InUserName") String login);

    @Procedure(name = "GetUserEncryptedPasswordByLogin")
    String GetUserEncryptedPasswordByLogin(@Param("InUserName") String login);


    @Procedure(procedureName = "REGISTER_USER")
    void saveUser(@Param("user_login") String login, @Param("user_password") String password, @Param("user_email") String email, @Param("user_role_id") Long id);


}