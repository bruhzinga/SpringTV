package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Entity.UsersView;
import by.zvor.springtv.Security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserViewService usersViewService;

    @Autowired
    public UserDetailsServiceImpl(UserViewService usersViewService) {

        this.usersViewService = usersViewService;
    }

    @Override

    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final UsersView user = this.usersViewService.findUserById(this.usersViewService.GetUserIdByUsername(username));

        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(user);
    }
}
