package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.FavouritesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Connection;
import java.sql.DriverManager;
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
public interface FavouritesViewRepository extends JpaRepository<FavouritesView, Integer> {


    /* @Procedure(name = "getUserFavouritesByUsername")*/
    default Collection<FavouritesView> getUserFavouritesByUsername(@Param("UserUsername") String username) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call getUserFavouritesByUsername(?,?)}");
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
        con.close();
        return arrayList;
    }

    /*@Procedure(procedureName = "add_favourite")*/
    default void addFavouriteToUser(@Param("userID") Long userId, @Param("movieID") Long filmId) throws ClassNotFoundException, SQLException {

        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call add_favourite(?,?)}");
        statement.setLong(1, userId);
        statement.setLong(2, filmId);
        statement.execute();
        con.close();

    }

    /*  @Procedure(procedureName = "delete_favourite")*/
    default void deleteFavouriteFromUser(@Param("userID") Long userId, @Param("movieID") Long filmId) throws ClassNotFoundException, SQLException {

        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call delete_favourite(?,?)}");
        statement.setLong(1, userId);
        statement.setLong(2, filmId);
        statement.execute();
        con.close();

    }

}