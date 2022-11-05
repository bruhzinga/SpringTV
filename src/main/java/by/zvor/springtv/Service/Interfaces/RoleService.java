package by.zvor.springtv.Service.Interfaces;

import by.zvor.springtv.Entity.Role;
import by.zvor.springtv.Entity.User;

import java.util.Collection;

public interface RoleService {
    Collection<Role> getAllRoles();
    Role getRoleById(int id);
}
