package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Entity.MovieActorsView;
import by.zvor.springtv.Entity.MovieMediaView;
import by.zvor.springtv.Entity.MoviesView;
import by.zvor.springtv.Repository.MovieMediaViewRepository;
import by.zvor.springtv.Repository.MoviesViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class MovieService {
    private final MovieMediaViewRepository movieMediaViewRepository;
    private final MoviesViewRepository moviesViewRepository;

    @Autowired
    public MovieService(MovieMediaViewRepository movieMediaViewRepository, MoviesViewRepository moviesViewRepository) {
        this.movieMediaViewRepository = movieMediaViewRepository;
        this.moviesViewRepository = moviesViewRepository;
    }


    @Transactional(readOnly = true)
    public Collection<MoviesView> getAllMoviesWithoutMedia() {
        return moviesViewRepository.getAllMoviesWithoutMedia();
    }

    @Transactional(readOnly = true)
    public MoviesView getMovieByIdNoMedia(int id) {
        return moviesViewRepository.getMovieByIdNoMedia(id);
    }

    @Transactional(readOnly = true)
    public MovieMediaView getMovieByIdWithMedia(int id) {
        return movieMediaViewRepository.getMovieByIdWithMedia(id);
    }

    @Transactional(readOnly = true)
    public Collection<MovieActorsView> getActorsByMovieId(long id) {
        return moviesViewRepository.getActorsByMovieId(id);
    }
}
