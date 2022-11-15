package by.zvor.springtv.DTO;

import by.zvor.springtv.Entity.MovieActorsView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieActorViewToUser {
    private String name;
    private String role;
    private Long actorId;
    private Long imageId;


    public MovieActorViewToUser(MovieActorsView actorsView) {
        this.name = actorsView.getName();
        this.role = actorsView.getRole();
        this.actorId = actorsView.getActorId();
        this.imageId = actorsView.getImageId();
    }
}
