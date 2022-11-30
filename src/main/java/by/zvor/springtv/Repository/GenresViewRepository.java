package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import by.zvor.springtv.Entity.GenresView;
import org.springframework.data.repository.query.Param;
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


    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    public GenresViewRepository() throws SQLException {
    }


    /* @Procedure(name = "getAllGenres")*/
    public Collection<GenresView> getAllGenres() throws ClassNotFoundException, SQLException {
        var statement = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.getAllGenres(?)}");
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

        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.addGenre(?)}");
        statement.setString(1, genreName);
        statement.execute();

    }

    /* @Procedure(name = "deleteGenre")*/
    public void deleteGenre(@Param("genreID") Long id) throws SQLException, ClassNotFoundException {
        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.deleteGenre(?)}");
        statement.setLong(1, id);
        statement.execute();


    }
}