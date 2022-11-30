package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import by.zvor.springtv.Entity.FavouritesView;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
//create or replace procedure getUserFavouritesByUsername(UserUsername IN varchar2, result OUT SYS_REFCURSOR) is
//begin
//    Open result for select * from FAVOURITES_VIEW where USERNAME = UserUsername;
//end;

/*create or replace procedure add_favourite(userID number, movieID number) is
        begin
        insert into favourites(USER_ID, MOVIE_ID)
        values (userID, movieID);
        end add_favourite;*/

/*create or replace procedure delete_favourite(userID number, movieID number) is
        begin
        delete
        from favourites
        where FAVOURITES.user_id = userId
        and movie_id = movieID;
        end delete_favourite;*/
@Repository
public class FavouritesViewRepository {

    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    public FavouritesViewRepository() throws SQLException {
    }

    /* @Procedure(name = "getUserFavouritesByUsername")*/
    public Collection<FavouritesView> getUserFavouritesByUsername(@Param("UserUsername") String username) throws ClassNotFoundException, SQLException {

        var statement = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.getUserFavouritesByUsername(?,?)}");
        statement.setString(1, username);
        statement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
        statement.execute();
        var resultSet = statement.getObject(2, java.sql.ResultSet.class);
        var arrayList = new ArrayList<FavouritesView>();
        while (resultSet.next()) {
            var favouritesView = new FavouritesView();
            favouritesView.setId(resultSet.getInt("ID"));
            favouritesView.setTitle(resultSet.getString("TITLE"));
            favouritesView.setUsername(resultSet.getString("USERNAME"));
            arrayList.add(favouritesView);
        }

        return arrayList;
    }

    /*@Procedure(procedureName = "add_favourite")*/
    public void addFavouriteToUser(@Param("userID") Long userId, @Param("movieID") Long filmId) throws ClassNotFoundException, SQLException {
        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.addFavourite(?,?)}");
        statement.setLong(1, userId);
        statement.setLong(2, filmId);
        statement.execute();


    }

    /*  @Procedure(procedureName = "delete_favourite")*/
    public void deleteFavouriteFromUser(@Param("userID") Long userId, @Param("movieID") Long filmId) throws ClassNotFoundException, SQLException {

        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.deleteFavourite(?,?)}");
        statement.setLong(1, userId);
        statement.setLong(2, filmId);
        statement.execute();


    }

}