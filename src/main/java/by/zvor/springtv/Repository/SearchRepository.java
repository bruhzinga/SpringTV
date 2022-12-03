package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import by.zvor.springtv.Entity.ImagesView;
import by.zvor.springtv.Entity.MoviesView;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class SearchRepository {
    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    public SearchRepository() throws SQLException {


    }

    //procedure SearchTables(tableName IN varchar2, columnName varchar2, searchParameters IN varchar2,
//                           OracleText IN boolean, result OUT SYS_REFCURSOR)
    public Collection<MoviesView> SearchMovies(String columnName, String searchParameters, boolean oracleText) throws SQLException, ClassNotFoundException {
        var rs = ExecuteSearch("MOVIES_VIEW", columnName, searchParameters, oracleText);
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
        return movies;
    }

    public Collection<ImagesView> SearchImages(String columnName, String searchParameters, boolean oracleText) throws SQLException {
        var rs = ExecuteSearch("IMAGES_VIEW", columnName, searchParameters, oracleText);
        var Images = new ArrayList<ImagesView>();
        while (rs.next()) {
            var Image = new ImagesView();
            Image.setId(rs.getInt("ID"));
            Image.setName(rs.getString("NAME"));
            Image.setType(rs.getString("TYPE"));
            Images.add(Image);
        }
        return Images;

    }


    public ResultSet ExecuteSearch(String tableName, String columnName, String searchParameters, boolean oracleText) throws SQLException {
        CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.SearchTables(?,?,?,?,?)}");
        stmt.setString(1, tableName);
        stmt.setString(2, columnName);
        stmt.setString(3, searchParameters);
        if (oracleText) {
            stmt.setInt(4, 1);
        } else {
            stmt.setInt(4, 0);
        }
        stmt.registerOutParameter(5, java.sql.Types.REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(5);
        return rs;
    }


}
