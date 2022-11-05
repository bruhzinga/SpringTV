package by.zvor.springtv.Service;

import by.zvor.springtv.Entity.UsersView;
import by.zvor.springtv.Repository.UsersViewRepository;
import by.zvor.springtv.Service.Interfaces.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserViewServiceImpl implements UserViewService {
    UsersViewRepository usersViewRepository;
    @Autowired
    public UserViewServiceImpl(UsersViewRepository usersViewRepository) {
        this.usersViewRepository = usersViewRepository;
    }

    @Override
    public Collection<UsersView> getAllUsers() {
        return (Collection<UsersView>) usersViewRepository.findAll();
    }
}
