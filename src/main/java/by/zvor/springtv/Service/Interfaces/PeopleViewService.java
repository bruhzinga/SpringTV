package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Entity.PeopleView;
import by.zvor.springtv.Repository.PeopleViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class PeopleViewService {

    private final PeopleViewRepository peopleViewRepository;

    @Autowired
    public PeopleViewService(PeopleViewRepository peopleViewRepository) {
        this.peopleViewRepository = peopleViewRepository;
    }

    @Transactional(readOnly = true)
    public Collection<PeopleView> GetAllActors() throws SQLException, ClassNotFoundException {
        return peopleViewRepository.findAllByProfession("actor");
    }

    @Transactional(readOnly = true)
    public Collection<PeopleView> GetAllDirectors() throws SQLException, ClassNotFoundException {
        return peopleViewRepository.findAllByProfession("director");
    }

    public void addActor(String name, Long photoId) throws SQLException, ClassNotFoundException {
        peopleViewRepository.addActor(name, photoId);
    }

    public void addDirector(String name, Long photoId) throws SQLException, ClassNotFoundException {
        peopleViewRepository.addDirector(name, photoId);
    }

    public void addActorToMovie(Long actorId, Long movieId, String role) throws SQLException, ClassNotFoundException {
        peopleViewRepository.addActorToMovie(actorId, movieId, role);
    }
    
}
