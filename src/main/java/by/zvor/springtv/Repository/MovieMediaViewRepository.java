package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.MovieMediaView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
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


    @Autowired
    @Qualifier("UserJdbcTemplate")
    private JdbcTemplate jdbcTemplateUser;

    public MovieMediaView getMovieByIdWithMedia(@Param("movieId") int id) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplateUser.getDataSource().getConnection();
        var statement = con.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.getMovieByIdWithMedia(?,?)}");
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


        return movieMediaView;
    }
}