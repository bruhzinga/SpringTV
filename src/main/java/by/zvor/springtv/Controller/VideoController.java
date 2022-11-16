package by.zvor.springtv.Controller;

import by.zvor.springtv.Service.Interfaces.VideosViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/video/")
@CrossOrigin("*")
public class VideoController {

    private final VideosViewService videoService;

    @Autowired
    public VideoController(VideosViewService videoService) {
        this.videoService = videoService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addNewImage(@RequestPart String name, @RequestPart byte[] video, @RequestPart String type) throws SQLException, ClassNotFoundException {
        videoService.addNewVideo(name, video, type);
        return new ResponseEntity<>("Video added", HttpStatus.OK);
    }


}
