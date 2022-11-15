package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.PeopleView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface PeopleViewRepository extends JpaRepository<PeopleView, Integer> {

    @Procedure(name = "findAllByProfession")
    Collection<PeopleView> findAllByProfession(@Param("ProfessionIn") String actor);

    @Procedure(name = "addActor")
    void addActor(@Param("actorName") String name, @Param("photoId") Long photoId);

    @Procedure(name = "addDirector")
    void addDirector(@Param("directorName") String name, @Param("photoId") Long photoId);

    @Procedure(name = "addActorToMovie")
    void addActorToMovie(@Param("actorId") Long actorId, @Param("movieId") Long movieId);
}