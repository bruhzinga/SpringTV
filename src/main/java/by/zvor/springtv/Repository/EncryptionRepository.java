package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class EncryptionRepository {
    Connection AdminConnection = DataSourceAdmin.getConnection();

    {

    }

    public EncryptionRepository() throws SQLException {
    }

    public String DecryptPassword(String EncryptedPassword) throws SQLException {
        var statement = AdminConnection.prepareCall("{?=call SPRINGTVADMIN.ADMINPACKAGE.DecryptPassword(?)}");
        statement.registerOutParameter(1, java.sql.Types.VARCHAR);
        statement.setString(2, EncryptedPassword);
        statement.execute();
        var pass = statement.getString(1);
        statement.close();
        return pass;
    }
}
