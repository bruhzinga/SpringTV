package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Entity.GenresView;
import by.zvor.springtv.Repository.GenresViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class GenresViewService {

    private final GenresViewRepository genresRepository;

    @Autowired
    public GenresViewService(GenresViewRepository genresRepository) {
        this.genresRepository = genresRepository;
    }


    @Transactional(readOnly = true)
    public Collection<GenresView> getAllGenres() throws SQLException, ClassNotFoundException {
        return genresRepository.getAllGenres();
    }

    public void addGenre(String genreName) throws SQLException, ClassNotFoundException {
        genresRepository.addGenre(genreName);
    }

    public void deleteGenre(Long id) throws SQLException, ClassNotFoundException {
        genresRepository.deleteGenre(id);

    }
}
