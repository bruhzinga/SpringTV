package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.FavouritesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface FavouritesViewRepository extends JpaRepository<FavouritesView, Integer> {

    @Procedure(name = "getUserFavouritesByUsername")
    Collection<FavouritesView> getUserFavouritesByUsername(@Param("UserUsername") String username);

    @Procedure(procedureName = "add_favourite")
    void addFavouriteToUser(@Param("userID") Long userId, @Param("movieID") Long filmId);

    @Procedure(procedureName = "delete_favourite")
    void deleteFavouriteFromUser(@Param("userID") Long userId, @Param("movieID") Long filmId);

}