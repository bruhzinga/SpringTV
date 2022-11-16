package by.zvor.springtv.Controller;

import by.zvor.springtv.DTO.MovieActorViewToUser;
import by.zvor.springtv.Entity.CommentsView;
import by.zvor.springtv.Entity.MoviesView;
import by.zvor.springtv.Service.Interfaces.CommentsViewService;
import by.zvor.springtv.Service.Interfaces.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/movies/")
@CrossOrigin("*")
public class MovieController {
    private final MovieService movieService;
    private final CommentsViewService commentsService;


    @Autowired
    public MovieController(MovieService movieService, CommentsViewService commentsService) {
        this.movieService = movieService;
        this.commentsService = commentsService;
    }

    @GetMapping(value = "allwithoutmedia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MoviesView>> getAllMoviesWithoutMedia() {
        var movies = movieService.getAllMoviesWithoutMedia();
        return new ResponseEntity<Collection<MoviesView>>(movies, HttpStatus.OK);
    }

    @GetMapping(value = "nomedia/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MoviesView> getMovieById(@PathVariable("id") int id) {
        return new ResponseEntity<MoviesView>(movieService.getMovieByIdNoMedia(id), HttpStatus.OK);
    }


    @GetMapping(value = "media/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> getMovieWithMediaById(@PathVariable("id") int id) {

        var movie = movieService.getMovieByIdWithMedia(id);
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("ID", movie.getId());
        formData.add("IMAGE_NAME", movie.getImageName());
        formData.add("IMAGE", movie.getImage());
        formData.add("VIDEO_NAME", movie.getVideoName());
        formData.add("VIDEO", movie.getVideo());
        return new ResponseEntity<MultiValueMap<String, Object>>(formData, HttpStatus.OK);

    }

    @GetMapping(value = "{id}/actors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MovieActorViewToUser>> getActorsByMovieId(@PathVariable("id") long id) {
        var actors = movieService.getActorsByMovieId(id);
        var actorsToUser = actors.stream().map(MovieActorViewToUser::new).toList();
        return new ResponseEntity<Collection<MovieActorViewToUser>>(actorsToUser, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentsView>> getCommentsByMovieId(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        var comments = commentsService.getCommentsByMovieId(id);
        return new ResponseEntity<Collection<CommentsView>>(comments, HttpStatus.OK);
    }

}
