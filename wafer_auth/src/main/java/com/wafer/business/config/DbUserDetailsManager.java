package com.wafer.business.config;

import com.wafer.business.constant.EnableEnum;
import com.wafer.business.entity.User;
import com.wafer.business.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DbUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {

    @Resource
    UserServiceImpl userService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        User updateUser = new User();
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        int i = userService.updateUser(updateUser);

        return null;
    }

    @Override
    public void createUser(UserDetails user) {
        User createUser = new User();
        createUser.setUsername(user.getUsername());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setEnabled(EnableEnum.on.getKey());
        int i = userService.addUser(createUser);
    }

    @Override
    public void updateUser(UserDetails user) {
        User updateUser = new User();
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(updateUser);
    }

    @Override
    public void deleteUser(String username) {
        userService.deleteUser(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        User existUser = userService.getUser(username);
        return  existUser != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User existUser = userService.getUser(username);
        if (existUser == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return new org.springframework.security.core.userdetails.User(existUser.getUsername(), existUser.getPassword(), existUser.getEnabled() == 1, true, true, true, new ArrayList<GrantedAuthority>());
        }
    }
}
