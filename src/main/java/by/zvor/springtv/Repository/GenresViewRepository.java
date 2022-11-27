package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.GenresView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
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
public class GenresViewRepository {
    @Autowired
    @Qualifier("AdminJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /* @Procedure(name = "getAllGenres")*/
    public Collection<GenresView> getAllGenres() throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
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
       
        return arrayList;

    }

    /*@Procedure(name = "addGenre")*/
    public void addGenre(String genreName) throws SQLException, ClassNotFoundException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        var statement = con.prepareCall("{call addGenre(?)}");
        statement.setString(1, genreName);
        statement.execute();
       
    }

    /* @Procedure(name = "deleteGenre")*/
    public void deleteGenre(@Param("genreID") Long id) throws SQLException, ClassNotFoundException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        var statement = con.prepareCall("{call deleteGenre(?)}");
        statement.setLong(1, id);
        statement.execute();
       
    }
}