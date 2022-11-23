package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.HistoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collection;

@Repository
public class HistoryViewRepository {
    @Autowired
    @Qualifier("AdminJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public void addHistory(Long userId, int id) throws SQLException {
        var connection = jdbcTemplate.getDataSource().getConnection();
        var statement = connection.prepareCall("{call addHistory(?,?)}");
        statement.setLong(1, userId);
        statement.setInt(2, id);
        statement.execute();
    }

    public Collection<HistoryView> getUserHistoryByUsername(String username) throws SQLException {
        var connection = jdbcTemplate.getDataSource().getConnection();
        var statement = connection.prepareCall("{call getUserHistoryByUsername(?,?)}");
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
        return history;


    }
}
