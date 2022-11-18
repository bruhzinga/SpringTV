package by.zvor.springtv.Repository;


import by.zvor.springtv.Entity.UsersView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
public interface UsersViewRepository extends JpaRepository<UsersView, Integer> {

    /*@Procedure(name = "getUserById")*/
    default UsersView getUserById(@Param("userId") Long id) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call getUserById(?,?)}");
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
        con.close();
        return user;
    }


    /* @Procedure(name = "GetUserIdByUsername")*/

    default Long GetUserIdByUsername(@Param("InUserName") String login) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call GetUserIdByUsername(?,?)}");
        stmt.setString(1, login);
        stmt.registerOutParameter(2, java.sql.Types.NUMERIC);
        stmt.execute();
        Long id = stmt.getLong(2);
        con.close();
        return id;
    }

    /*@Procedure(name = "GetUserEncryptedPasswordByLogin")*/
    default String GetUserEncryptedPasswordByLogin(@Param("InUserName") String login) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call GetUserEncryptedPasswordByLogin(?,?)}");
        stmt.setString(1, login);
        stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
        stmt.execute();
        String password = stmt.getString(2);
        con.close();
        return password;
    }


    /*   @Procedure(procedureName = "REGISTER_USER")*/
    default void saveUser(@Param("user_login") String login, @Param("user_password") String password, @Param("user_email") String email, @Param("user_role_id") Long id) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call REGISTER_USER(?,?,?,?)}");
        statement.setString(1, login);
        statement.setString(2, password);
        statement.setString(3, email);
        statement.setLong(4, id);
        statement.execute();
        con.close();

    }


}