package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.DTO.CommentFromUser;
import by.zvor.springtv.Entity.CommentsView;
import by.zvor.springtv.Repository.CommentsViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class CommentsViewService {
    private final CommentsViewRepository commentsRepository;

    @Autowired
    public CommentsViewService(CommentsViewRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public void postComment(CommentFromUser favouritesFromClient, Long userId) throws SQLException, ClassNotFoundException {
        commentsRepository.postComment(userId, favouritesFromClient.getFilmId(), favouritesFromClient.getComment());
    }

    public Collection<CommentsView> getCommentsByMovieId(long id) throws SQLException, ClassNotFoundException {
        return commentsRepository.getCommentsByMovieId(id);
    }
}
