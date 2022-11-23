package by.zvor.springtv.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieFromClient {
    //title, year,description, director, genre id ,director id,video id,trailer id,image id
    private String title;
    private int year;
    private String description;
    private int directorId;
    private int genreId;
    private int videoId;
    private int trailerId;
    private int imageId;

}
