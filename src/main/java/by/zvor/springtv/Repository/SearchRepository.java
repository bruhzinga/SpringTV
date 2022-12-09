package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SearchRepository {
    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    public SearchRepository() throws SQLException {


    }

    //procedure SearchTables(tableName IN varchar2, columnName varchar2, searchParameters IN varchar2,
//                           OracleText IN boolean, result OUT SYS_REFCURSOR)


    public ResultSet ExecuteSearch(String tableName, String columnName, String searchParameters, boolean oracleText) throws SQLException {
        CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.SearchTables(?,?,?,?,?)}");
        stmt.setString(1, tableName.toUpperCase());
        stmt.setString(2, columnName.toUpperCase());
        stmt.setString(3, searchParameters);
        if (oracleText) {
            stmt.setInt(4, 1);
        } else {
            stmt.setInt(4, 0);
        }
        stmt.registerOutParameter(5, java.sql.Types.REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(5);
        return rs;
    }


}
