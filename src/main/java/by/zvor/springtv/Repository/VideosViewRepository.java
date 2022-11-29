package by.zvor.springtv.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class VideosViewRepository {
    @Autowired
    @Qualifier("AdminJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public void addNewVideo(@Param("videoName") String name, @Param("video") byte[] video, @Param("videoType") String type) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        var statement = con.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.AddNewVideo(?,?,?)}");
        statement.setString(1, name);
        statement.setBytes(2, video);
        statement.setString(3, type);
        statement.execute();


    }
}