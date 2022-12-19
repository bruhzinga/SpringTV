package by.zvor.springtv.Controller;

import by.zvor.springtv.DTO.MovieActorViewToUser;
import by.zvor.springtv.DTO.MovieFromClient;
import by.zvor.springtv.DTO.SearchFromUser;
import by.zvor.springtv.Entity.CommentsView;
import by.zvor.springtv.Entity.MoviesView;
import by.zvor.springtv.Service.Interfaces.CommentsViewService;
import by.zvor.springtv.Service.Interfaces.HistoryViewService;
import by.zvor.springtv.Service.Interfaces.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/movies/")
@CrossOrigin("*")
public class MovieController {
    private final MovieService movieService;
    private final CommentsViewService commentsService;

    private final HistoryViewService historyService;


    @Autowired
    public MovieController(MovieService movieService, CommentsViewService commentsService, CommentsViewService commentsViewService, HistoryViewService historyService) {
        this.movieService = movieService;
        this.commentsService = commentsService;
        this.historyService = historyService;
    }

    @GetMapping(value = "allwithoutmedia/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MoviesView>> getAllMoviesWithoutMedia(@PathVariable("page") int page) throws SQLException, ClassNotFoundException {
        var movies = movieService.getAllMoviesWithoutMedia(page);
        return new ResponseEntity<Collection<MoviesView>>(movies, HttpStatus.OK);
    }

    @GetMapping(value = "nomedia/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MoviesView> getMovieById(@PathVariable("id") int id) throws SQLException, ClassNotFoundException {
        return new ResponseEntity<MoviesView>(movieService.getMovieByIdNoMedia(id), HttpStatus.OK);
    }


    @GetMapping(value = "video/{id}")
    public ResponseEntity<byte[]> getMovieWithMediaById(@PathVariable("id") int id) throws SQLException, ClassNotFoundException {
        var movie = movieService.getMovieByIdWithMedia(id);


        var video = movie.getVideo();
        var videoName = movie.getVideoName();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        historyService.addHistory(username, id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .header("Content-Disposition", "attachment; filename=\"" + videoName + "\"")
                .body(video);

    }

    @GetMapping("trailer/{id}")
    public ResponseEntity<byte[]> getMovieTrailerById(@PathVariable("id") int id) throws SQLException, ClassNotFoundException {
        var movie = movieService.getMovieByIdWithMedia(id);
        var trailer = movie.getTrailerVideo();
        var trailerName = movie.getTrailerName();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp4"))
                .header("Content-Disposition", "attachment; filename=\"" + trailerName + "\"")
                .body(trailer);
    }


    @GetMapping(value = "{id}/actors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MovieActorViewToUser>> getActorsByMovieId(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        var actors = movieService.getActorsByMovieId(id);
        var actorsToUser = actors.stream().map(MovieActorViewToUser::new).toList();
        return new ResponseEntity<Collection<MovieActorViewToUser>>(actorsToUser, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentsView>> getCommentsByMovieId(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        var comments = commentsService.getCommentsByMovieId(id);
        return new ResponseEntity<Collection<CommentsView>>(comments, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    //title, year,description, director, genre id ,director id,video id,trailer id,image id
    public ResponseEntity<String> addNewMovie(@RequestBody MovieFromClient movie) throws SQLException, ClassNotFoundException {
        /* movieService.addNewMovie(title, year, description, directorId, genreId, videoId, trailerId, imageId);*/
        var id = movieService.addNewMovie(movie.getTitle(), movie.getYear(), movie.getDescription(), movie.getDirectorId(), movie.getGenreId(), movie.getVideoId(), movie.getTrailerId(), movie.getImageId());
        return new ResponseEntity<>("Movie added with id " + id + " added", HttpStatus.OK);
    }


    @GetMapping(value = "getmoviesbyActor/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MoviesView>> getMoviesByActorId(@PathVariable("id") long id, @RequestBody SearchFromUser search) throws SQLException, ClassNotFoundException {
        var movies = movieService.getMoviesByActorId(id, search.getPage());
        return new ResponseEntity<Collection<MoviesView>>(movies, HttpStatus.OK);
    }

    @GetMapping(value = "getmoviesbyDirector/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MoviesView>> getMoviesByDirectorId(@PathVariable("id") long id, @RequestBody SearchFromUser search) throws SQLException, ClassNotFoundException {
        var movies = movieService.getMoviesByDirectorId(id, search.getPage());
        return new ResponseEntity<Collection<MoviesView>>(movies, HttpStatus.OK);
    }

    @PostMapping(value = "SearchMovies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MoviesView>> searchMovies(@RequestBody SearchFromUser searchFromUser) throws SQLException, ClassNotFoundException {
        var movies = movieService.SearchMovies(searchFromUser);
        return new ResponseEntity<Collection<MoviesView>>(movies, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateMovie(@PathVariable("id") long id, @RequestBody MovieFromClient movie) throws SQLException, ClassNotFoundException {
        movieService.updateMovie(id, movie.getTitle(), movie.getYear(), movie.getDescription(), movie.getDirectorId(), movie.getGenreId(), movie.getVideoId(), movie.getTrailerId(), movie.getImageId());
        return new ResponseEntity<>("Movie with id " + id + " updated", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        movieService.deleteMovie(id);
        return new ResponseEntity<>("Movie with id " + id + " deleted", HttpStatus.OK);
    }


}
