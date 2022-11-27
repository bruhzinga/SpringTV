package by.zvor.springtv.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;

public class ImagesViewRepository {

    @Autowired
    @Qualifier("AdminJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public void addNewImage(@Param("imageName") String name, @Param("image") byte[] image, @Param("ImType") String type) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        var statement = con.prepareCall("{call AddNewImage(?,?,?)}");
        statement.setString(1, name);
        statement.setBytes(2, image);
        statement.setString(3, type);
        statement.execute();
       
    }

    public byte[] getThumbnail(Long movieId) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        java.sql.CallableStatement stmt = con.prepareCall("{call GetThumbnailByMovieId(?,?)}");
        stmt.setLong(1, movieId);
        stmt.registerOutParameter(2, java.sql.Types.BLOB);
        stmt.execute();
        java.sql.Blob blob = stmt.getBlob(2);
        var image = blob.getBytes(1, (int) blob.length());
       
        return image;
    }

    public byte[] getPersonImage(Long personId) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        java.sql.CallableStatement stmt = con.prepareCall("{call GetPersonImagebyId(?,?)}");
        stmt.setLong(1, personId);
        stmt.registerOutParameter(2, java.sql.Types.BLOB);
        stmt.execute();
        java.sql.Blob blob = stmt.getBlob(2);
        var image = blob.getBytes(1, (int) blob.length());
       
        return image;
    }

    public void deleteImage(Long id) throws ClassNotFoundException, SQLException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        var statement = con.prepareCall("{call DeleteImageById(?)}");
        statement.setLong(1, id);
        statement.execute();
       


    }

    public void updateImage(Long id, String name, byte[] image, String type) throws SQLException, ClassNotFoundException {
        Connection con = jdbcTemplate.getDataSource().getConnection();
        var statement = con.prepareCall("{call UpdateImageById(?,?,?,?)}");
        statement.setLong(1, id);
        statement.setString(2, name);
        statement.setBytes(3, image);
        statement.setString(4, type);
        statement.execute();
       

    }
}