package by.zvor.springtv.Repository;


import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import by.zvor.springtv.Entity.UsersView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/*create or replace procedure getUserById(userId IN number, result OUT sys_refcursor) is
        begin
        open result for
        select * from USERS_VIEW where ID = userId;
        end getUserById;



        create or replace procedure GetUserIdByUsername(InUserName IN varchar2, outID OUT number) is
        begin
        select id into outID from users where username = InUserName;
        if outID is null then
        raise_application_error(-20001, 'User not found');
        end if;
        end GetUserIdByUsername;


        create or replace procedure GetUserEncryptedPasswordByLogin(InUserName IN varchar2, password OUT varchar2) is
        begin
        select password_hash into password from users where username = InUserName;
        if password is null then
        raise_application_error(-20001, 'User not found');
        end if;
        end GetUserEncryptedPasswordByLogin;*/

@Repository
public class UsersViewRepository {

    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    @Autowired
    SearchRepository searchRepository;


    public UsersViewRepository() throws SQLException {
    }

    /*@Procedure(name = "getUserById")*/
    public UsersView getUserById(@Param("userId") Long id) throws ClassNotFoundException, SQLException {

        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.getUserById(?,?)}");
        stmt.setLong(1, id);
        stmt.registerOutParameter(2, java.sql.Types.REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);
        var user = new UsersView();
        while (rs.next()) {
            user.setId(rs.getLong("ID"));
            user.setUsername(rs.getString("USERNAME"));
            user.setPasswordHash(rs.getString("PASSWORD_HASH"));
            user.setRole(rs.getString("ROLE"));
        }
        stmt.close();
        rs.close();
        return user;
    }


    /* @Procedure(name = "GetUserIdByUsername")*/

    public Long GetUserIdByUsername(@Param("InUserName") String login) throws ClassNotFoundException, SQLException {

        java.sql.CallableStatement stmt = AdminConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.GetUserIdByUsername(?,?)}");
        stmt.setString(1, login);
        stmt.registerOutParameter(2, java.sql.Types.NUMERIC);
        stmt.execute();
        Long id = stmt.getLong(2);
        stmt.close();

        return id;
    }

    /*@Procedure(name = "GetUserEncryptedPasswordByLogin")*/
    public String GetUserEncryptedPasswordByLogin(@Param("InUserName") String login) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.GetUserEncryptedPasswordByLogin(?,?)}");
        stmt.setString(1, login);
        stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
        stmt.execute();
        var pass = stmt.getString(2);
        stmt.close();
        return pass;
    }


    /*   @Procedure(procedureName = "REGISTER_USER")*/
    public void saveUser(@Param("user_login") String login, @Param("user_password") String password, @Param("user_email") String email, @Param("user_role_id") Long id) throws ClassNotFoundException, SQLException {

        var statement = AdminConnection.prepareCall("{call ADMINPACKAGE.RegisterUser(?,?,?,?)}");
        statement.setString(1, login);
        statement.setString(2, password);
        statement.setString(3, email);
        statement.setLong(4, id);
        statement.execute();
        statement.close();


    }

    public Optional<String> findUserPasswordByEmail(String email) throws ClassNotFoundException, SQLException {
        email = "=" + "'" + email + "'";
        var res = searchRepository.ExecuteSearch("USERS", "EMAIL", email, false);
        ResultSet rs = (ResultSet) res[0];
        CallableStatement stmt = (CallableStatement) res[1];
        Optional<String> password = Optional.empty();
        while (rs.next()) {
            password = Optional.of(rs.getString("PASSWORD_HASH"));

        }
        rs.close();
        stmt.close();

        return password;

    }


}