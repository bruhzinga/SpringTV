package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class VideosViewRepository {
    Connection AdminConnection = DataSourceAdmin.getConnection();

    public VideosViewRepository() throws SQLException {
    }

    public int addNewVideo(@Param("videoName") String name, @Param("video") byte[] video, @Param("videoType") String type) throws ClassNotFoundException, SQLException {
        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.AddNewVideo(?,?,?,?)}");
        statement.setString(1, name);
        statement.setBytes(2, video);
        statement.setString(3, type);
        statement.registerOutParameter(4, java.sql.Types.INTEGER);
        statement.execute();
        return statement.getInt(4);


    }
}