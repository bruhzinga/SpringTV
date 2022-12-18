package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import by.zvor.springtv.Entity.HistoryView;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

@Repository
public class HistoryViewRepository {
    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    public HistoryViewRepository() throws SQLException {
    }

    public void addHistory(Long userId, int id) throws SQLException {

        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.addHistory(?,?)}");
        statement.setLong(1, userId);
        statement.setInt(2, id);
        statement.execute();
        statement.close();
    }

    public Collection<HistoryView> getUserHistoryByUsername(String username) throws SQLException {
        var statement = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.getUserHistoryByUsername(?,?)}");
        statement.setString(1, username);
        statement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
        statement.execute();
        var rs = (java.sql.ResultSet) statement.getObject(2);
        var history = new java.util.ArrayList<HistoryView>();
        while (rs.next()) {
            var historyView = new HistoryView();
            historyView.setUsername(rs.getString("USERNAME"));
            historyView.setTitle(rs.getString("TITLE"));
            historyView.setTime(rs.getString("TIME"));
            historyView.setId(rs.getLong("ID"));
            history.add(historyView);
        }
        rs.close();
        statement.close();
        return history;


    }
}
