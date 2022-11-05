package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Entity.UsersView;

import java.util.Collection;

public interface UserViewService {
    Collection<UsersView> getAllUsers();
}
