package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.DTO.VideoInfoToUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class VideosViewRepository {
    Connection AdminConnection = DataSourceAdmin.getConnection();

    @Autowired
    SearchRepository searchRepository;

    public VideosViewRepository() throws SQLException {
    }

    public int addNewVideo(String name, byte[] video, String type) throws ClassNotFoundException, SQLException {
        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.AddNewVideo(?,?,?,?)}");
        statement.setString(1, name);
        statement.setBytes(2, video);
        statement.setString(3, type);
        statement.registerOutParameter(4, java.sql.Types.INTEGER);
        statement.execute();
        var id = statement.getInt(4);
        statement.close();
        return id;


    }


    public Collection<VideoInfoToUser> SearchVideos(String tableName, String columnName, String searchParameters, boolean oracleText) throws SQLException {
        var res = searchRepository.ExecuteSearch(tableName, columnName, searchParameters, oracleText);
        ResultSet rs = (ResultSet) res[0];
        CallableStatement stmt = (CallableStatement) res[1];
        var videoInfoToUser = new ArrayList<VideoInfoToUser>();
        while (rs.next()) {
            VideoInfoToUser info = new VideoInfoToUser();
            info.setId(rs.getLong("ID"));
            info.setName(rs.getString("NAME"));
            info.setType(rs.getString("TYPE"));
            videoInfoToUser.add(info);
        }
        rs.close();
        stmt.close();
        return videoInfoToUser;
    }
}