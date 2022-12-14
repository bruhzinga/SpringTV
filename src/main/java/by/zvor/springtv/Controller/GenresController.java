package by.zvor.springtv.Controller;

import by.zvor.springtv.DTO.GenreFromUser;
import by.zvor.springtv.Entity.GenresView;
import by.zvor.springtv.Service.Interfaces.GenresViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/genres/")
@CrossOrigin("*")
public class GenresController {

    private final GenresViewService genresService;

    @Autowired
    public GenresController(GenresViewService genresService) {
        this.genresService = genresService;
    }

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GenresView>> getAllGenres() throws SQLException, ClassNotFoundException {
        return new ResponseEntity<Collection<GenresView>>(genresService.getAllGenres(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addGenre(@RequestBody GenreFromUser genre) throws SQLException, ClassNotFoundException {
        genresService.addGenre(genre.getName());
        return new ResponseEntity<String>("Genre added", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteGenre(@RequestBody GenresView genre) throws SQLException, ClassNotFoundException {
        genresService.deleteGenre(genre.getId());
        return new ResponseEntity<String>("Genre deleted", HttpStatus.OK);
    }


}
