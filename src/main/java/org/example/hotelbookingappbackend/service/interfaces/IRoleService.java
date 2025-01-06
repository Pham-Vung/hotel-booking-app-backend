package org.example.hotelbookingappbackend.service.interfaces;

import org.example.hotelbookingappbackend.entity.Role;
import org.example.hotelbookingappbackend.entity.User;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();
    Role createRole(Role theRole);
    void deleteRole(Long roleId);
    Role findByName(String name);
    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}
