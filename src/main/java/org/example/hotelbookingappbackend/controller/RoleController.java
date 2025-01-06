package org.example.hotelbookingappbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.hotelbookingappbackend.entity.Role;
import org.example.hotelbookingappbackend.entity.User;
import org.example.hotelbookingappbackend.exception.RoleAlreadyExistsException;
import org.example.hotelbookingappbackend.service.interfaces.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;

    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.FOUND);
    }

    @PostMapping("/create-new-role")
    public ResponseEntity<String> createRole(@RequestBody Role theRole) {
        try {
            roleService.createRole(theRole);
            return ResponseEntity.ok("Tạo quyền mới thành công");
        } catch (RoleAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId) {
        try {
            roleService.deleteRole(roleId);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @PostMapping("/remove-all-users-from-role/{roleId}")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId) {
        try {
            return roleService.removeAllUsersFromRole(roleId);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @PostMapping("/remove-user-from-role")
    public User removeUserFromRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId) {
        try {
            return roleService.removeUserFromRole(userId, roleId);
        } catch (UsernameNotFoundException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @PostMapping("/assign-user-to-role")
    public User assignRoleToUser(
            @RequestParam("userId") Long userId,
            @RequestParam("roleId") Long roleId) {
        try {
            return roleService.assignRoleToUser(userId, roleId);
        } catch (UsernameNotFoundException | IllegalArgumentException
                 | RoleAlreadyExistsException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
