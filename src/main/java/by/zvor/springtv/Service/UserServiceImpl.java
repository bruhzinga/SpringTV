package by.zvor.springtv.Service;

import by.zvor.springtv.DTO.UserInfo;
import by.zvor.springtv.Entity.User;
import by.zvor.springtv.Repository.UserRepository;
import by.zvor.springtv.Service.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> getAllUsers() {
        return (Collection<User>) userRepository.findAll();
    }
    @Override
    public Collection<UserInfo> getAllUsersProjection() {
        return (Collection<UserInfo>) userRepository.findAllBy();
    }
}
