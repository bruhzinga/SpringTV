package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.CommentsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.sql.SQLException;

public interface CommentsViewRepository extends JpaRepository<CommentsView, Integer> {
    @Procedure(procedureName = "ADD_COMMENT")
    void postComment(@Param("userID") Long userId, @Param("movieID") Long movieId, @Param("comment_text") String commentText) throws SQLException;
}