package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.GenresView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
/*create or replace procedure getAllGenres(result OUT SYS_REFCURSOR) is
        begin
        Open result for select * from GENRES_VIEW;
        end;

        create or replace procedure addGenre(genreName IN varchar2) is
        begin
        insert into genres(NAME)
        values (genreName);
        end addGenre;

        create or replace procedure deleteGenre(genreID IN number) is
        begin
        delete
        from genres
        where genres.id = genreID;
        end deleteGenre;*/

@Repository
public interface GenresViewRepository extends JpaRepository<GenresView, Integer> {

    /* @Procedure(name = "getAllGenres")*/
    default Collection<GenresView> getAllGenres() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call getAllGenres(?)}");
        statement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
        statement.execute();
        var resultSet = statement.getObject(1, java.sql.ResultSet.class);
        var arrayList = new ArrayList<GenresView>();
        while (resultSet.next()) {
            var genresView = new GenresView();
            genresView.setId(resultSet.getLong("ID"));
            genresView.setName(resultSet.getString("NAME"));
            arrayList.add(genresView);
        }
        con.close();
        return arrayList;

    }

    /*@Procedure(name = "addGenre")*/
    default void addGenre(String genreName) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call addGenre(?)}");
        statement.setString(1, genreName);
        statement.execute();
        con.close();
    }

    /* @Procedure(name = "deleteGenre")*/
    default void deleteGenre(@Param("genreID") Long id) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call deleteGenre(?)}");
        statement.setLong(1, id);
        statement.execute();
        con.close();
    }
}