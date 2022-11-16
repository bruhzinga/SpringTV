package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.CommentsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

public interface CommentsViewRepository extends JpaRepository<CommentsView, Integer> {

    default void postComment(@Param("userID") Long userId, @Param("movieID") Long movieId, @Param("comment_text") String commentText) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call add_comment(?,?,?)}");
        statement.setLong(1, userId);
        statement.setLong(2, movieId);
        statement.setString(3, commentText);
        statement.execute();
        con.close();
    }


    default Collection<CommentsView> getCommentsByMovieId(long id) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "SpringTVAdmin", "9");
        var statement = con.prepareCall("{call GetCommentsByMovieId(?,?)}");
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
        con.close();
        return arrayList;
    }
}