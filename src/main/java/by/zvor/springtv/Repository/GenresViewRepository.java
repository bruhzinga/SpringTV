package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.GenresView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface GenresViewRepository extends JpaRepository<GenresView, Integer> {

    @Procedure(name = "getAllGenres")
    Collection<GenresView> getAllGenres();

    @Procedure(name = "addGenre")
    void addGenre(String genreName);

    @Procedure(name = "deleteGenre")
    void deleteGenre(@Param("genreID") Long id);
}