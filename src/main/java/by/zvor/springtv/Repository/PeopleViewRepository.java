package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import by.zvor.springtv.Entity.PeopleView;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
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

@Repository
public class PeopleViewRepository {

    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    public PeopleViewRepository() throws SQLException {
    }

    /* @Procedure(name = "findAllByProfession")*/
    public Collection<PeopleView> findAllByProfession(@Param("ProfessionIn") String actor) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.findAllByProfession(?,?)}");
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
        return people;
    }

    /*@Procedure(name = "addActor")*/
    public void addActor(@Param("actorName") String name, @Param("photoId") Long photoId) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.addActor(?,?)}");
        stmt.setString(1, name);
        stmt.setLong(2, photoId);
        stmt.execute();
    }

    /*@Procedure(name = "addDirector")*/
    public void addDirector(@Param("directorName") String name, @Param("photoId") Long photoId) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.addDirector(?,?)}");
        stmt.setString(1, name);
        stmt.setLong(2, photoId);
        stmt.execute();
    }

    /*@Procedure(procedureName = "addActorToMovie")*/
    public void addActorToMovie(@Param("actorId") Long actorId, @Param("movieId") Long movieId, @Param("RoleIn") String role) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.addActorToMovie(?,?,?)}");
        stmt.setLong(1, actorId);
        stmt.setLong(2, movieId);
        stmt.setString(3, role);
        stmt.execute();
    }
}