package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceUser;
import by.zvor.springtv.Entity.MovieMediaView;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
/*create or replace procedure getMovieByIdWithMedia(movieId IN number, result OUT SYS_REFCURSOR) is
        begin
        Open result for select * from MOVIE_MEDIA_VIEW where ID = movieId;
        end getMovieByIdWithMedia;*/


@Repository
public class MovieMediaViewRepository {
    /* @Procedure(name = "getMovieByIdWithMedia")*/


    Connection UserConnection = DataSourceUser.getConnection();

    public MovieMediaViewRepository() throws SQLException {
    }

    public MovieMediaView getMovieByIdWithMedia(int id) throws ClassNotFoundException, SQLException {
        var statement = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.getMovieByIdWithMedia(?,?)}");
        statement.setInt(1, id);
        statement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
        statement.execute();
        var resultSet = statement.getObject(2, java.sql.ResultSet.class);
        var movieMediaView = new MovieMediaView();
        while (resultSet.next()) {
            movieMediaView.setId(resultSet.getLong("ID"));
            movieMediaView.setImageName(resultSet.getString("IMAGE_NAME"));
            movieMediaView.setImage(resultSet.getBytes("IMAGE"));
            movieMediaView.setVideoName(resultSet.getString("VIDEO_NAME"));
            movieMediaView.setVideo(resultSet.getBytes("VIDEO"));
            movieMediaView.setTrailerName(resultSet.getString("TRAILER_NAME"));
            movieMediaView.setTrailerVideo(resultSet.getBytes("TRAILER_VIDEO"));
        }
        resultSet.close();
        statement.close();
        return movieMediaView;
    }
}