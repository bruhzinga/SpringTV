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

    @GetMapping(value = "getThumbnail/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getThumbnail(@PathVariable("id") Long id) throws SQLException, ClassNotFoundException {
        return new ResponseEntity<>(imagesService.getThumbnail(id), HttpStatus.OK);
    }


}
