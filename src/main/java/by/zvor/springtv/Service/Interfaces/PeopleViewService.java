package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Entity.PeopleView;
import by.zvor.springtv.Repository.PeopleViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class PeopleViewService {

    private final PeopleViewRepository peopleViewRepository;

    @Autowired
    public PeopleViewService(PeopleViewRepository peopleViewRepository) {
        this.peopleViewRepository = peopleViewRepository;
    }

    @Transactional(readOnly = true)
    public Collection<PeopleView> GetAllActors() {
        return peopleViewRepository.findAllByProfession("actor");
    }

    @Transactional(readOnly = true)
    public Collection<PeopleView> GetAllDirectors() {
        return peopleViewRepository.findAllByProfession("director");
    }

    public void addActor(String name, Long photoId) {
        peopleViewRepository.addActor(name, photoId);
    }

    public void addDirector(String name, Long photoId) {
        peopleViewRepository.addDirector(name, photoId);
    }

    public void addActorToMovie(Long actorId, Long movieId) {
        peopleViewRepository.addActorToMovie(actorId, movieId);
    }
}
