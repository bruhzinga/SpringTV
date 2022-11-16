package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.VideosView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface VideosViewRepository extends JpaRepository<VideosView, Integer> {

    default void addNewVideo(@Param("videoName") String name, @Param("video") byte[] video, @Param("videoType") String type) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call AddNewVideo(?,?,?)}");
        statement.setString(1, name);
        statement.setBytes(2, video);
        statement.setString(3, type);
        statement.execute();
        con.close();

    }
}