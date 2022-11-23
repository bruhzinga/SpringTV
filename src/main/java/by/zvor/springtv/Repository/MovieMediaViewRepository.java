package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.MovieMediaView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*create or replace procedure getMovieByIdWithMedia(movieId IN number, result OUT SYS_REFCURSOR) is
        begin
        Open result for select * from MOVIE_MEDIA_VIEW where ID = movieId;
        end getMovieByIdWithMedia;*/


public interface MovieMediaViewRepository extends JpaRepository<MovieMediaView, Integer> {
    /* @Procedure(name = "getMovieByIdWithMedia")*/
    default MovieMediaView getMovieByIdWithMedia(@Param("movieId") int id) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call getMovieByIdWithMedia(?,?)}");
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
        }

        con.close();
        return movieMediaView;
    }
}