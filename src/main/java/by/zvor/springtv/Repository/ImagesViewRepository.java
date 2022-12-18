package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import by.zvor.springtv.DTO.ImageInfoToUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class ImagesViewRepository {

    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    @Autowired
    private SearchRepository searchRepository;

    public ImagesViewRepository() throws SQLException {
    }

    public int addNewImage(@Param("imageName") String name, @Param("image") byte[] image, @Param("ImType") String type) throws ClassNotFoundException, SQLException {
        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.AddNewImage(?,?,?,?)}");
        statement.registerOutParameter(4, java.sql.Types.INTEGER);
        statement.setString(1, name);
        statement.setBytes(2, image);
        statement.setString(3, type);
        statement.execute();
        var id = statement.getInt(4);
        statement.close();
        return id;

    }

    public byte[] getThumbnail(Long movieId) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.GetThumbnailByMovieId(?,?)}");
        stmt.setLong(1, movieId);
        stmt.registerOutParameter(2, java.sql.Types.BLOB);
        stmt.execute();
        java.sql.Blob blob = stmt.getBlob(2);
        var image = blob.getBytes(1, (int) blob.length());
        blob.free();
        stmt.close();
        return image;
    }

    public byte[] getPersonImage(Long personId) throws ClassNotFoundException, SQLException {
        java.sql.CallableStatement stmt = UserConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.GetPersonImagebyId(?,?)}");
        stmt.setLong(1, personId);
        stmt.registerOutParameter(2, java.sql.Types.BLOB);
        stmt.execute();
        java.sql.Blob blob = stmt.getBlob(2);
        var image = blob.getBytes(1, (int) blob.length());
        stmt.close();
        blob.free();
        return image;
    }

    public void deleteImage(Long id) throws ClassNotFoundException, SQLException {
        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.DeleteImageById(?)}");
        statement.setLong(1, id);
        statement.execute();
        statement.close();


    }

    public void updateImage(Long id, String name, byte[] image, String type) throws SQLException, ClassNotFoundException {
        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.UpdateImageById(?,?,?,?)}");
        statement.setLong(1, id);
        statement.setString(2, name);
        statement.setBytes(3, image);
        statement.setString(4, type);
        statement.execute();
        statement.close();


    }

    public Collection<ImageInfoToUser> SearchImages(String columnName, String searchParameters, boolean oracleText) throws SQLException {
        var res = searchRepository.ExecuteSearch("IMAGES_VIEW", columnName, searchParameters, oracleText);
        ResultSet rs = (ResultSet) res[0];
        CallableStatement stmt = (CallableStatement) res[1];
        var Images = new ArrayList<ImageInfoToUser>();
        while (rs.next()) {
            var Image = new ImageInfoToUser();
            Image.setId(rs.getLong("ID"));
            Image.setName(rs.getString("NAME"));
            Image.setType(rs.getString("TYPE"));
            Images.add(Image);
        }
        rs.close();
        stmt.close();
        return Images;

    }

}