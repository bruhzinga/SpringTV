package by.zvor.springtv.Service;

import by.zvor.springtv.Entity.Role;
import by.zvor.springtv.Entity.User;
import by.zvor.springtv.Repository.RoleRepository;
import by.zvor.springtv.Service.Interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Collection<Role> getAllRoles() {
        return (Collection<Role>) roleRepository.findAll();
    }
    @Override
    public Role getRoleById(int id) {
        return roleRepository.findById(id).get();
    }
}

