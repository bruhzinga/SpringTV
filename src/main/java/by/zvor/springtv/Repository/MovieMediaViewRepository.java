package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.MovieMediaView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface MovieMediaViewRepository extends JpaRepository<MovieMediaView, Integer> {
    @Procedure(name = "getMovieByIdWithMedia")
    MovieMediaView getMovieByIdWithMedia(@Param("movieId") int id);
}