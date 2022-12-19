package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import by.zvor.springtv.Entity.MovieActorsView;
import by.zvor.springtv.Entity.MoviesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    @Autowired
    private SearchRepository searchRepository;

    public MoviesViewRepository() throws SQLException {
    }

    public Collection<MoviesView> getAllMoviesWithoutMedia(int page) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.getAllMoviesWithoutMedia(?,?)}");
        stmt.setInt(2, page);
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
        rs.close();
        stmt.close();

        return movies;
    }

    /*@Procedure(name = "getMovieByIdWithoutMedia")*/
    public MoviesView getMovieByIdNoMedia(@Param("movieId") int id) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.getMovieByIdWithoutMedia(?,?)}");
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
        rs.close();
        stmt.close();
        return movie;

    }

    /* @Procedure(name = "getActorsByMovieId")*/
    public Collection<MovieActorsView> getActorsByMovieId(@Param("movieId") long id) throws
            ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.getActorsByMovieId(?,?)}");
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
        rs.close();
        stmt.close();
        return actors;
    }


    /* procedure AddNewMovie(movieName IN varchar2, movieDescription IN varchar2,
                           movieYear IN number,
                           ImageID IN number, VideoID IN number, GenreID IN number, DirectorID IN number,
                           TrailerID IN number, movieId OUT number) is*/
    public int addNewMovie(String title, int year, String description, int directorId, int genreId, int videoId, int trailerId, int imageId) throws SQLException {
        java.sql.CallableStatement stmt = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.AddNewMovie(?,?,?,?,?,?,?,?,?)}");
        stmt.setString(1, title);
        stmt.setString(2, description);
        stmt.setInt(3, year);
        stmt.setInt(4, imageId);
        stmt.setInt(5, videoId);
        stmt.setInt(6, genreId);
        stmt.setInt(7, directorId);
        stmt.setInt(8, trailerId);
        stmt.registerOutParameter(9, Types.INTEGER);
        stmt.execute();
        var id = stmt.getInt(9);
        stmt.close();
        return id;


    }

    public Collection<MoviesView> getMoviesByActorId(long id, int page) throws SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.getMoviesByActorId(?,?)}");
        stmt.setLong(1, id);
        stmt.registerOutParameter(2, java.sql.Types.REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);
        var movies = new java.util.ArrayList<MoviesView>();
        int i = 0;
        for (i = 0; i < (page - 1) * 50; i++)
            rs.next();
        i = 0;

        while (rs.next() && i < 50) {
            var movie = new MoviesView();
            movie.setId(rs.getInt("ID"));
            movie.setTitle(rs.getString("TITLE"));
            movie.setYear(rs.getShort("YEAR"));
            movie.setDescription(rs.getString("DESCRIPTION"));
            movie.setNumberOfViews(rs.getInt("NUMBER_OF_VIEWS"));
            movie.setDirector(rs.getString("DIRECTOR"));
            movie.setGenre(rs.getString("GENRE"));
            movies.add(movie);
            i++;
        }
        rs.close();
        stmt.close();
        return movies;
    }

    public Collection<MoviesView> getMoviesByDirectorId(long id, long page) throws SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{ call SPRINGTVADMIN.USERPACKAGE.getMoviesByDirectorId(?,?)}");
        stmt.setLong(1, id);
        stmt.registerOutParameter(2, java.sql.Types.REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);
        var movies = new java.util.ArrayList<MoviesView>();
        int i = 0;
        for (i = 0; i < (page - 1) * 50; i++)
            rs.next();
        i = 0;
        while (rs.next() && i < 50) {
            var movie = new MoviesView();
            movie.setId(rs.getInt("ID"));
            movie.setTitle(rs.getString("TITLE"));
            movie.setYear(rs.getShort("YEAR"));
            movie.setDescription(rs.getString("DESCRIPTION"));
            movie.setNumberOfViews(rs.getInt("NUMBER_OF_VIEWS"));
            movie.setDirector(rs.getString("DIRECTOR"));
            movie.setGenre(rs.getString("GENRE"));
            movies.add(movie);
            i++;
        }
        rs.close();
        stmt.close();
        return movies;
    }

    public Collection<MoviesView> SearchMovies(String columnName, String searchParameters, boolean oracleText, int page) throws SQLException, ClassNotFoundException {
        var res = searchRepository.ExecuteSearch("MOVIES_VIEW", columnName, searchParameters, oracleText);
        ResultSet rs = (ResultSet) res[0];
        CallableStatement stmt = (CallableStatement) res[1];

        var movies = new java.util.ArrayList<MoviesView>();
        int i = 0;
        for (i = 0; i < (page - 1) * 50; i++)
            rs.next();
        i = 0;
        while (rs.next() && i < 50) {
            var movie = new MoviesView();
            movie.setId(rs.getInt("ID"));
            movie.setTitle(rs.getString("TITLE"));
            movie.setYear(rs.getShort("YEAR"));
            movie.setDescription(rs.getString("DESCRIPTION"));
            movie.setNumberOfViews(rs.getInt("NUMBER_OF_VIEWS"));
            movie.setDirector(rs.getString("DIRECTOR"));
            movie.setGenre(rs.getString("GENRE"));
            movies.add(movie);
            i++;
        }
        rs.close();
        stmt.close();

        return movies;

    }

    public void updateMovie(long id, String title, int year, String description, int directorId, int genreId, int videoId, int trailerId, int imageId) throws SQLException {

        java.sql.CallableStatement stmt = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.updateMovieById(?,?,?,?,?,?,?,?,?)}");
        stmt.setLong(1, id);
        stmt.setString(2, title);
        stmt.setString(3, description);
        stmt.setInt(4, year);
        stmt.setInt(5, imageId);
        stmt.setInt(6, videoId);
        stmt.setInt(7, genreId);
        stmt.setInt(8, directorId);
        stmt.setInt(9, trailerId);
        stmt.execute();
        stmt.close();

    }

    public void deleteMovie(long id) throws SQLException {
        var stmt = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.deleteMovieById(?)}");
        stmt.setLong(1, id);
        stmt.execute();
        stmt.close();


    }
}