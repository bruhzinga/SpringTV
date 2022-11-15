package by.zvor.springtv.Controller;

import by.zvor.springtv.Entity.ImagesView;
import by.zvor.springtv.Entity.MovieActorsView;
import by.zvor.springtv.Entity.PeopleView;
import by.zvor.springtv.Service.Interfaces.PeopleViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/people/")
@CrossOrigin("*")
public class PeopleController {
    private final PeopleViewService peopleViewService;

    @Autowired
    public PeopleController(PeopleViewService peopleViewService) {
        this.peopleViewService = peopleViewService;
    }


    @GetMapping(value = "allActors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PeopleView>> getAllActors() {
        return new ResponseEntity<Collection<PeopleView>>(peopleViewService.GetAllActors(), HttpStatus.OK);
    }

    @GetMapping(value = "allDirectors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PeopleView>> getAllDirectors() {
        return new ResponseEntity<Collection<PeopleView>>(peopleViewService.GetAllDirectors(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "addActor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addActor(@RequestBody PeopleView actor) {
        peopleViewService.addActor(actor.getName(), actor.getPhotoId());
        return new ResponseEntity<String>("Actor added", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "addDirector", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addDirector(@RequestBody PeopleView director) {
        peopleViewService.addDirector(director.getName(), director.getPhotoId());
        return new ResponseEntity<String>("Director added", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "AddActorToMovie", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addActorToMovie(@RequestBody MovieActorsView actorsView) {
        peopleViewService.addActorToMovie(actorsView.getActorId(), actorsView.getMovieId(), actorsView.getRole());
        return new ResponseEntity<String>("Actor added to movie", HttpStatus.OK);
    }

    //TODO
    @GetMapping(value = "actor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImagesView> getActorById(@PathVariable("id") Long id) {
        return new ResponseEntity<ImagesView>(peopleViewService.getPhotoOfActorById(id), HttpStatus.OK);
    }

    //TODO
    @GetMapping(value = "director/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ImagesView> getDirectorById(@PathVariable("id") Long id) {
        return new ResponseEntity<ImagesView>(peopleViewService.getPhotoOfDirectorById(id), HttpStatus.OK);
    }

    /*@GetMapping(value = "actor/{id}/movies", produces = MediaType.APPLICATION_JSON_VALUE)//TODO*/

    /*@GetMapping(value = "director/{id}/movies", produces = MediaType.APPLICATION_JSON_VALUE)//TODO*/


}