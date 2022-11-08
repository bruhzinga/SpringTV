package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.DTO.FavouritesFromClient;
import by.zvor.springtv.DTO.UnauthorizedUser;
import by.zvor.springtv.DTO.UnregisteredUser;
import by.zvor.springtv.Entity.FavouritesView;
import by.zvor.springtv.Entity.UsersView;
import by.zvor.springtv.Repository.FavouritesViewRepository;
import by.zvor.springtv.Repository.UsersViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserViewService {
    private final UsersViewRepository userRepository;
    private final FavouritesViewRepository favouritesRepository;

    @Autowired
    public UserViewService(UsersViewRepository userRepository, FavouritesViewRepository favouritesRepository) {
        this.userRepository = userRepository;
        this.favouritesRepository = favouritesRepository;
    }

    public UsersView findUserById(int id) {
        return userRepository.getUserById(id);
    }

    public Long GetUserIdByLogin(String login) {
        return userRepository.GetUserIdByUsername(login);
    }

    public void login(final UnauthorizedUser unauthorizedUser) {

        final String login = unauthorizedUser.getLogin();
        final String password = unauthorizedUser.getPassword();

        if (null == userRepository.GetUserIdByUsername(login)) {
            throw new BadCredentialsException("User with login " + login + " not found");
        }

        final UsersView userFromRepository = this.userRepository.getUserById(this.userRepository.GetUserIdByUsername(login));
        if (null == userFromRepository) {
            throw new BadCredentialsException("Wrong password");
        }
        System.out.println(unauthorizedUser.getPassword() + " " + userFromRepository.getPasswordHash());
    }


    public void register(final UnregisteredUser user) {

        if (null != userRepository.GetUserIdByUsername(user.getLogin())) {
            throw new IllegalArgumentException("User with login " + user.getLogin() + " already exists");
        }

        try {
            this.userRepository.saveUser(user.getLogin(), user.getPassword(), user.getEmail(), user.getRoleId());
        } catch (final DataAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Collection<FavouritesView> getUserFavouritesByUsername(String username) {
        return favouritesRepository.getUserFavouritesByUsername(username);
    }

    public String getUsernameFromUserId(Long userId) {
        return userRepository.getUsernameFromUserId(userId);
    }


    public void addFavoriteToUser(FavouritesFromClient favouritesFromClient, Long userId) {
        favouritesRepository.addFavouriteToUser(userId, favouritesFromClient.getId());
    }

    public void deleteFavoriteFromUser(FavouritesFromClient favouritesFromClient, Long userId) {
        favouritesRepository.deleteFavouriteFromUser(userId, favouritesFromClient.getId());
    }


}
