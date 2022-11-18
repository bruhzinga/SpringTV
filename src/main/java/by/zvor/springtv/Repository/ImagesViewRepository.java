package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.ImagesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface ImagesViewRepository extends JpaRepository<ImagesView, Integer> {


    default void addNewImage(@Param("imageName") String name, @Param("image") byte[] image, @Param("ImType") String type) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call AddNewImage(?,?,?)}");
        statement.setString(1, name);
        statement.setBytes(2, image);
        statement.setString(3, type);
        statement.execute();
        con.close();
    }

    default byte[] getThumbnail(Long movieId) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call GetThumbnailByMovieId(?,?)}");
        stmt.setLong(1, movieId);
        stmt.registerOutParameter(2, java.sql.Types.BLOB);
        stmt.execute();
        java.sql.Blob blob = stmt.getBlob(2);
        var image = blob.getBytes(1, (int) blob.length());
        con.close();
        return image;
    }

    default byte[] getPersonImage(Long personId) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        java.sql.CallableStatement stmt = con.prepareCall("{call GetPersonImagebyId(?,?)}");
        stmt.setLong(1, personId);
        stmt.registerOutParameter(2, java.sql.Types.BLOB);
        stmt.execute();
        java.sql.Blob blob = stmt.getBlob(2);
        var image = blob.getBytes(1, (int) blob.length());
        con.close();
        return image;
    }

    default void deleteImage(Long id) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call DeleteImageById(?)}");
        statement.setLong(1, id);
        statement.execute();
        con.close();


    }

    default void updateImage(Long id, String name, byte[] image, String type) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call UpdateImageById(?,?,?,?)}");
        statement.setLong(1, id);
        statement.setString(2, name);
        statement.setBytes(3, image);
        statement.setString(4, type);
        statement.execute();
        con.close();

    }
}