package org.example.hotelbookingappbackend.service.implement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.hotelbookingappbackend.entity.Role;
import org.example.hotelbookingappbackend.entity.User;
import org.example.hotelbookingappbackend.exception.UserAlreadyExistsException;
import org.example.hotelbookingappbackend.repository.RoleRepository;
import org.example.hotelbookingappbackend.repository.UserRepository;
import org.example.hotelbookingappbackend.service.interfaces.IUserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User resgisterUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail() + " đã tồn tại");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy quyền ROLE_USER"));
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email);
        if (theUser != null) {
            userRepository.deleteByEmail(email);
        }
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng"));
    }
}
