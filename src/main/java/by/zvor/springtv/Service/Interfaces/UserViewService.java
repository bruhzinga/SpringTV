package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.DTO.FavouritesFromClient;
import by.zvor.springtv.DTO.UnauthorizedUser;
import by.zvor.springtv.DTO.UnregisteredUser;
import by.zvor.springtv.Entity.FavouritesView;
import by.zvor.springtv.Entity.HistoryView;
import by.zvor.springtv.Entity.UsersView;
import by.zvor.springtv.Repository.EncryptionRepository;
import by.zvor.springtv.Repository.FavouritesViewRepository;
import by.zvor.springtv.Repository.HistoryViewRepository;
import by.zvor.springtv.Repository.UsersViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserViewService {
    private final UsersViewRepository userRepository;
    private final FavouritesViewRepository favouritesRepository;

    private final HistoryViewRepository historyRepository;

    private final EncryptionRepository encryptionRepository;


    @Autowired
    public UserViewService(UsersViewRepository userRepository, FavouritesViewRepository favouritesRepository, HistoryViewRepository historyRepository, EncryptionRepository encryptionRepository) {
        this.userRepository = userRepository;
        this.favouritesRepository = favouritesRepository;
        this.historyRepository = historyRepository;

        this.encryptionRepository = encryptionRepository;
    }

    @Transactional(readOnly = true)
    public UsersView findUserById(long id) throws SQLException, ClassNotFoundException {
        return userRepository.getUserById(id);
    }

    @Transactional
    public Long GetUserIdByLogin(String login) throws SQLException, ClassNotFoundException {
        return userRepository.GetUserIdByUsername(login);
    }

    @Transactional
    public void login(final UnauthorizedUser unauthorizedUser) throws SQLException, ClassNotFoundException {

        final String login = unauthorizedUser.getLogin();
        final String password = unauthorizedUser.getPassword();
        final var encryptedPassword = userRepository.GetUserEncryptedPasswordByLogin(login);
        var decryptedPassword = encryptionRepository.DecryptPassword(encryptedPassword);
        if (!decryptedPassword.equals(password)) {
            throw new BadCredentialsException("Wrong password");

        }
    }


    public void register(final UnregisteredUser user) throws SQLException, ClassNotFoundException {

        try {
            this.userRepository.saveUser(user.getLogin(), user.getPassword(), user.getEmail(), user.getRoleId());
        } catch (final DataAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Collection<FavouritesView> getUserFavouritesByUsername(String username) throws SQLException, ClassNotFoundException {
        return favouritesRepository.getUserFavouritesByUsername(username);
    }


    public void addFavoriteToUser(FavouritesFromClient favouritesFromClient, Long userId) throws SQLException, ClassNotFoundException {
        favouritesRepository.addFavouriteToUser(userId, favouritesFromClient.getId());
    }

    public void deleteFavoriteFromUser(FavouritesFromClient favouritesFromClient, Long userId) throws SQLException, ClassNotFoundException {
        favouritesRepository.deleteFavouriteFromUser(userId, favouritesFromClient.getId());
    }


    @Transactional(readOnly = true)
    public Long GetUserIdByUsername(String username) throws SQLException, ClassNotFoundException {
        return userRepository.GetUserIdByUsername(username);
    }

    public Collection<HistoryView> getUserHistoryByUsername(String username) throws SQLException, ClassNotFoundException {
        return historyRepository.getUserHistoryByUsername(username);
    }

    public Optional<String> findUserPasswordByEmail(String email) throws SQLException, ClassNotFoundException {
        return userRepository.findUserPasswordByEmail(email);
    }
}
