package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class ImagesViewRepository {

    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    public ImagesViewRepository() throws SQLException {
    }

    public int addNewImage(@Param("imageName") String name, @Param("image") byte[] image, @Param("ImType") String type) throws ClassNotFoundException, SQLException {
        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.AddNewImage(?,?,?,?)}");
        statement.registerOutParameter(4, java.sql.Types.INTEGER);
        statement.setString(1, name);
        statement.setBytes(2, image);
        statement.setString(3, type);
        statement.execute();
        return statement.getInt(4);

    }

    public byte[] getThumbnail(Long movieId) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.GetThumbnailByMovieId(?,?)}");
        stmt.setLong(1, movieId);
        stmt.registerOutParameter(2, java.sql.Types.BLOB);
        stmt.execute();
        java.sql.Blob blob = stmt.getBlob(2);
        var image = blob.getBytes(1, (int) blob.length());

        return image;
    }

    public byte[] getPersonImage(Long personId) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.GetPersonImagebyId(?,?)}");
        stmt.setLong(1, personId);
        stmt.registerOutParameter(2, java.sql.Types.BLOB);
        stmt.execute();
        java.sql.Blob blob = stmt.getBlob(2);
        var image = blob.getBytes(1, (int) blob.length());

        return image;
    }

    public void deleteImage(Long id) throws ClassNotFoundException, SQLException {
        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.DeleteImageById(?)}");
        statement.setLong(1, id);
        statement.execute();


    }

    public void updateImage(Long id, String name, byte[] image, String type) throws SQLException, ClassNotFoundException {
        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.UpdateImageById(?,?,?,?)}");
        statement.setLong(1, id);
        statement.setString(2, name);
        statement.setBytes(3, image);
        statement.setString(4, type);
        statement.execute();


    }
}