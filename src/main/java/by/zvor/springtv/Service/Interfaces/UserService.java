package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.DTO.UserInfo;
import by.zvor.springtv.Entity.User;

import java.util.Collection;

public interface UserService {

    Collection<User> getAllUsers();

    Collection<UserInfo> getAllUsersProjection();
}
