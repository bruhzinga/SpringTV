package by.zvor.springtv.Controller;

import by.zvor.springtv.Service.Interfaces.ImagesViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/images/")
@CrossOrigin("*")
public class ImageController {

    private final ImagesViewService imagesService;

    @Autowired
    public ImageController(ImagesViewService imagesService) {
        this.imagesService = imagesService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addNewImage(@RequestPart String name, @RequestPart byte[] image, @RequestPart String type) throws SQLException, ClassNotFoundException {
        imagesService.addNewImage(name, image, type);
        return new ResponseEntity<>("Image added", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> UpdateImage(@RequestPart String name, @RequestPart byte[] image, @RequestPart String type, @PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        imagesService.updateImage(id, name, image, type);
        return new ResponseEntity<>("Image updated", HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteImage(@PathVariable Long id) throws SQLException, ClassNotFoundException {
        imagesService.deleteImage(id);
        return new ResponseEntity<>("Image deleted", HttpStatus.OK);
    }


    @GetMapping(value = "getThumbnail/{movieId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getThumbnail(@PathVariable("movieId") Long id) throws SQLException, ClassNotFoundException {
        return new ResponseEntity<>(imagesService.getThumbnail(id), HttpStatus.OK);
    }

    @GetMapping(value = "people/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getPersonImage(@PathVariable("id") Long id) throws SQLException, ClassNotFoundException {
        return new ResponseEntity<>(imagesService.getPersonImage(id), HttpStatus.OK);
    }

}
