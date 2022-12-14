package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.DTO.SearchFromUser;
import by.zvor.springtv.Entity.MovieActorsView;
import by.zvor.springtv.Entity.MovieMediaView;
import by.zvor.springtv.Entity.MoviesView;
import by.zvor.springtv.Repository.MovieMediaViewRepository;
import by.zvor.springtv.Repository.MoviesViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
    public Collection<MoviesView> getAllMoviesWithoutMedia(int page) throws SQLException, ClassNotFoundException {
        return moviesViewRepository.getAllMoviesWithoutMedia(page);
    }

    @Transactional(readOnly = true)
    public MoviesView getMovieByIdNoMedia(int id) throws SQLException, ClassNotFoundException {
        return moviesViewRepository.getMovieByIdNoMedia(id);
    }

    @Transactional(readOnly = true)
    public MovieMediaView getMovieByIdWithMedia(int id) throws SQLException, ClassNotFoundException {
        return movieMediaViewRepository.getMovieByIdWithMedia(id);
    }

    @Transactional(readOnly = true)
    public Collection<MovieActorsView> getActorsByMovieId(long id) throws SQLException, ClassNotFoundException {
        return moviesViewRepository.getActorsByMovieId(id);
    }

    public int addNewMovie(String title, int year, String description, int directorId, int genreId, int videoId, int trailerId, int imageId) throws SQLException {
        return moviesViewRepository.addNewMovie(title, year, description, directorId, genreId, videoId, trailerId, imageId);
    }

    public Collection<MoviesView> getMoviesByActorId(long id, int page) throws SQLException {
        return moviesViewRepository.getMoviesByActorId(id, page);
    }

    public Collection<MoviesView> getMoviesByDirectorId(long id, long page) throws SQLException {
        return moviesViewRepository.getMoviesByDirectorId(id, page);

    }

    public Collection<MoviesView> SearchMovies(SearchFromUser searchFromUser) throws SQLException, ClassNotFoundException {
        return moviesViewRepository.SearchMovies(searchFromUser.getColumnName(), searchFromUser.getSearchParameters(), searchFromUser.isOracleText(), searchFromUser.getPage());
    }

    public void updateMovie(long id, String title, int year, String description, int directorId, int genreId, int videoId, int trailerId, int imageId) throws SQLException {
        moviesViewRepository.updateMovie(id, title, year, description, directorId, genreId, videoId, trailerId, imageId);
    }

    public void deleteMovie(long id) throws SQLException {
        moviesViewRepository.deleteMovie(id);

    }
}
