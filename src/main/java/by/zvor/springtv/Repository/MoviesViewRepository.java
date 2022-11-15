package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.MoviesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MoviesViewRepository extends JpaRepository<MoviesView, Integer> {
    @Procedure(name = "getAllMoviesWithoutMedia")
    Collection<MoviesView> getAllMoviesWithoutMedia();

    @Procedure(name = "getMovieByIdWithoutMedia")
    MoviesView getMovieByIdNoMedia(@Param("movieId") int id);
}