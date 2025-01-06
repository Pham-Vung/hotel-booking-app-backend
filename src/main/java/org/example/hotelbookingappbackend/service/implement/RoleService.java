package org.example.hotelbookingappbackend.service.implement;

import lombok.RequiredArgsConstructor;
import org.example.hotelbookingappbackend.entity.Role;
import org.example.hotelbookingappbackend.entity.User;
import org.example.hotelbookingappbackend.exception.RoleAlreadyExistsException;
import org.example.hotelbookingappbackend.exception.UserAlreadyExistsException;
import org.example.hotelbookingappbackend.repository.RoleRepository;
import org.example.hotelbookingappbackend.repository.UserRepository;
import org.example.hotelbookingappbackend.service.interfaces.IRoleService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_" + theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if (roleRepository.existsByName(roleName)) {
            throw new RoleAlreadyExistsException("Quyền " + role.getName() + " đã tồn tại");
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository
                .findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với tên " + name));
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
//        Optional<User> user = userRepository.findById(userId);
//        Optional<Role> role = roleRepository.findById(roleId);
//        if (user.isPresent() && role.get().getUsers().contains(user.get())) {
//            role.get().removeUserFromRole(user.get());
//            roleRepository.save(role.get());
//            return user.get();
//        }
//        throw new UsernameNotFoundException("Không tìm thấy người dùng");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với ID: " + userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy vai trò với ID: " + roleId));

        if (!role.getUsers().contains(user)) {
            throw new IllegalArgumentException("Người dùng không có vai trò được chỉ định");
        }

        role.removeUserFromRole(user);
        roleRepository.save(role);
        return user;
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với ID: " + userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy vai trò với ID: " + roleId));

        if (user.getRoles().contains(role)) {
            throw new UserAlreadyExistsException("Người dùng với ID: " + userId + " đã có quyền với ID: " + roleId);
        }

        role.assignRoleToUser(user);
        roleRepository.save(role);
        return user;
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy quyền với ID: " + roleId));
        role.removeAllUsersFromRole();
        return roleRepository.save(role);
    }
}
