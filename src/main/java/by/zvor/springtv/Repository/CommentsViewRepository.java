package by.zvor.springtv.Repository;

import by.zvor.springtv.Config.DataSourceAdmin;
import by.zvor.springtv.Config.DataSourceUser;
import by.zvor.springtv.Entity.CommentsView;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class CommentsViewRepository {

    Connection AdminConnection = DataSourceAdmin.getConnection();
    Connection UserConnection = DataSourceUser.getConnection();

    public CommentsViewRepository() throws SQLException {
    }


    public void postComment(@Param("userID") Long userId, @Param("movieID") Long movieId, @Param("comment_text") String commentText) throws SQLException, ClassNotFoundException {

        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.ADMINPACKAGE.addComment(?,?,?)}");
        statement.setLong(1, userId);
        statement.setLong(2, movieId);
        statement.setString(3, commentText);
        statement.execute();
        statement.close();

    }


    public Collection<CommentsView> getCommentsByMovieId(long id) throws ClassNotFoundException, SQLException {

        var statement = AdminConnection.prepareCall("{call SPRINGTVADMIN.USERPACKAGE.GetCommentsByMovieId(?,?)}");
        statement.setLong(1, id);
        statement.registerOutParameter(2, Types.REF_CURSOR);

        statement.execute();
        var resultSet = statement.getObject(2, java.sql.ResultSet.class);
        var arrayList = new ArrayList<CommentsView>();
        while (resultSet.next()) {
            var commentsView = new CommentsView();
            commentsView.setId(resultSet.getInt("ID"));
            commentsView.setComment(resultSet.getString("COMMENT"));
            commentsView.setTitle(resultSet.getString("TITLE"));
            commentsView.setUsername(resultSet.getString("USERNAME"));
            arrayList.add(commentsView);
        }
        resultSet.close();
        statement.close();

        return arrayList;
    }
}