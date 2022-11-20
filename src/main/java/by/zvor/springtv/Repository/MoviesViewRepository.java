package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.MovieActorsView;
import by.zvor.springtv.Entity.MoviesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

/*
create or replace procedure getAllMoviesWithoutMedia(result OUT SYS_REFCURSOR) is
        begin
        Open result for select * from MOVIES_VIEW;
        end getAllMoviesWithoutMedia;*/
/*
create or replace procedure getMovieByIdWithoutMedia(movieId IN number, result OUT SYS_REFCURSOR) is
        begin
        Open result for select * from MOVIES_VIEW where id = movieId;
        end getMovieByIdWithoutMedia;*/
/*
create or replace procedure getActorsByMovieId(movieId IN number, result OUT SYS_REFCURSOR) is
        begin
        Open result for select *
        from MOVIE_ACTORS_VIEW
        where MOVIE_ID = movieId;
        end getActorsByMovieId;*/
@Repository
public class MoviesViewRepository {
    /*@Procedure(name
     = "getAllMoviesWithoutMedia")*/

    @Autowired
    @Qualifier("AdminJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public Collection<MoviesView> getAllMoviesWithoutMedia() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call getAllMoviesWithoutMedia(?)}");
        stmt.registerOutParameter(1, java.sql.Types.REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(1);
        var movies = new java.util.ArrayList<MoviesView>();
        while (rs.next()) {
            var movie = new MoviesView();
            movie.setId(rs.getInt("ID"));
            movie.setTitle(rs.getString("TITLE"));
            movie.setYear(rs.getShort("YEAR"));
            movie.setDescription(rs.getString("DESCRIPTION"));
            movie.setNumberOfViews(rs.getInt("NUMBER_OF_VIEWS"));
            movie.setDirector(rs.getString("DIRECTOR"));
            movie.setGenre(rs.getString("GENRE"));
            movies.add(movie);
        }
        con.close();
        return movies;
    }

    /*@Procedure(name = "getMovieByIdWithoutMedia")*/
    public MoviesView getMovieByIdNoMedia(@Param("movieId") int id) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        java.sql.CallableStatement stmt = con.prepareCall("{call getMovieByIdWithoutMedia(?,?)}");
        stmt.setInt(1, id);
        stmt.registerOutParameter(2, java.sql.Types.REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);
        var movie = new MoviesView();
        while (rs.next()) {
            movie.setId(rs.getInt("ID"));
            movie.setTitle(rs.getString("TITLE"));
            movie.setYear(rs.getShort("YEAR"));
            movie.setDescription(rs.getString("DESCRIPTION"));
            movie.setNumberOfViews(rs.getInt("NUMBER_OF_VIEWS"));
            movie.setDirector(rs.getString("DIRECTOR"));
            movie.setGenre(rs.getString("GENRE"));
        }

        return movie;

    }

    /* @Procedure(name = "getActorsByMovieId")*/
    public Collection<MovieActorsView> getActorsByMovieId(@Param("movieId") long id) throws
            ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call getActorsByMovieId(?,?)}");
        stmt.setLong(1, id);
        stmt.registerOutParameter(2, java.sql.Types.REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);
        var actors = new java.util.ArrayList<MovieActorsView>();
        while (rs.next()) {
            var actor = new MovieActorsView();
            actor.setId(rs.getLong("ID"));
            actor.setMovieId(rs.getLong("MOVIE_ID"));
            actor.setTitle(rs.getString("TITLE"));
            actor.setActorId(rs.getLong("ACTOR_ID"));
            actor.setName(rs.getString("NAME"));
            actor.setImageId(rs.getLong("IMAGE_ID"));
            actor.setRole(rs.getString("Role"));
            actors.add(actor);
        }
        con.close();
        return actors;
    }
}