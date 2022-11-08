package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Entity.UsersView;
import by.zvor.springtv.Repository.UsersViewRepository;
import by.zvor.springtv.Security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersViewRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(final UsersViewRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final UsersView user = this.userRepository.getUserById(this.userRepository.GetUserIdByUsername(username));

        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(user);
    }
}
