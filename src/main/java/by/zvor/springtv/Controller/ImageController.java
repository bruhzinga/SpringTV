package by.zvor.springtv.Controller;

import by.zvor.springtv.Service.Interfaces.ImagesViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images/")
@CrossOrigin("*")
public class ImageController {

    private final ImagesViewService imagesService;

    @Autowired
    public ImageController(ImagesViewService imagesService) {
        this.imagesService = imagesService;
    }


    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addNewImage(@RequestPart String name, @RequestPart byte[] image, @RequestPart String type) {
        imagesService.addNewImage(name, image, type);
        return new ResponseEntity<>("Image added", HttpStatus.OK);
    }


}
