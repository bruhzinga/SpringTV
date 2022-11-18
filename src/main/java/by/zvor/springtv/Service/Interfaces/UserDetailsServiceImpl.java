package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Entity.UsersView;
import by.zvor.springtv.Security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserViewService usersViewService;

    @Autowired
    public UserDetailsServiceImpl(UserViewService usersViewService) {

        this.usersViewService = usersViewService;
    }

    @Override

    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final UsersView user;
        try {
            user = this.usersViewService.findUserById(this.usersViewService.GetUserIdByUsername(username));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(user);
    }
}
