package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.PeopleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

/*create or replace procedure findAllByProfession(professionIn IN varchar2, result OUT SYS_REFCURSOR) is
        begin
        Open result for select * from PEOPLE_VIEW where professionIn = profession;
        end findAllByProfession;

        create or replace procedure addActor(actorName IN varchar2, photoId IN number) is
        begin
        insert into people(NAME, PHOTO_ID, PROFESSION)
        values (actorName, photoId, 'actor');
        end addActor;

        create or replace procedure addDirector(directorName IN varchar2, photoId IN number) is
        begin
        insert into people(NAME, PHOTO_ID, PROFESSION)
        values (directorName, photoId, 'director');
        end addDirector;*/

public interface PeopleViewRepository extends JpaRepository<PeopleView, Integer> {

    /* @Procedure(name = "findAllByProfession")*/
    default Collection<PeopleView> findAllByProfession(@Param("ProfessionIn") String actor) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call findAllByProfession(?,?)}");
        stmt.setString(1, actor);
        stmt.registerOutParameter(2, java.sql.Types.REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);
        var people = new java.util.ArrayList<PeopleView>();
        while (rs.next()) {
            var person = new PeopleView();
            person.setId(rs.getLong("ID"));
            person.setName(rs.getString("NAME"));
            person.setProfession(rs.getString("PROFESSION"));
            person.setPhotoId(rs.getLong("PHOTO_ID"));
            people.add(person);
        }
        con.close();
        return people;
    }

    /*@Procedure(name = "addActor")*/
    default void addActor(@Param("actorName") String name, @Param("photoId") Long photoId) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call addActor(?,?)}");
        stmt.setString(1, name);
        stmt.setLong(2, photoId);
        stmt.execute();
        con.close();
    }

    /*@Procedure(name = "addDirector")*/
    default void addDirector(@Param("directorName") String name, @Param("photoId") Long photoId) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call addDirector(?,?)}");
        stmt.setString(1, name);
        stmt.setLong(2, photoId);
        stmt.execute();
        con.close();
    }

    /*@Procedure(procedureName = "addActorToMovie")*/
    default void addActorToMovie(@Param("actorId") Long actorId, @Param("movieId") Long movieId, @Param("RoleIn") String role) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call addActorToMovie(?,?,?)}");
        stmt.setLong(1, actorId);
        stmt.setLong(2, movieId);
        stmt.setString(3, role);
        stmt.execute();
        con.close();
    }
}