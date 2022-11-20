package by.zvor.springtv.Repository;


import by.zvor.springtv.Entity.UsersView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
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
public class UsersViewRepository {

    @Autowired
    @Qualifier("AdminJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /*@Procedure(name = "getUserById")*/
    public UsersView getUserById(@Param("userId") Long id) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
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

        return user;
    }


    /* @Procedure(name = "GetUserIdByUsername")*/

    public Long GetUserIdByUsername(@Param("InUserName") String login) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        java.sql.CallableStatement stmt = con.prepareCall("{call GetUserIdByUsername(?,?)}");
        stmt.setString(1, login);
        stmt.registerOutParameter(2, java.sql.Types.NUMERIC);
        stmt.execute();
        Long id = stmt.getLong(2);

        return id;
    }

    /*@Procedure(name = "GetUserEncryptedPasswordByLogin")*/
    public String GetUserEncryptedPasswordByLogin(@Param("InUserName") String login) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        java.sql.CallableStatement stmt = con.prepareCall("{call GetUserEncryptedPasswordByLogin(?,?)}");
        stmt.setString(1, login);
        stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
        stmt.execute();
        String password = stmt.getString(2);

        return password;
    }


    /*   @Procedure(procedureName = "REGISTER_USER")*/
    public void saveUser(@Param("user_login") String login, @Param("user_password") String password, @Param("user_email") String email, @Param("user_role_id") Long id) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        var statement = con.prepareCall("{call REGISTER_USER(?,?,?,?)}");
        statement.setString(1, login);
        statement.setString(2, password);
        statement.setString(3, email);
        statement.setLong(4, id);
        statement.execute();


    }


}