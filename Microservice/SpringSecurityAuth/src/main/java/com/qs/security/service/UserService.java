package com.qs.security.service;

import com.qs.security.dao.RoleDao;
import com.qs.security.dao.UserDao;
import com.qs.security.entity.Role;
import com.qs.security.entity.User;
import com.qs.security.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private UserDao userDao;

    private RoleDao roleDao;

    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("Username does not exist");
        }

        return AuthUserDetail.builder() // spring security's userDetail
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(getAuthoritiesFromUser(user))
                .user_id(user.getUser_id())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();

        if (user == null || user.getRoles() == null) return userAuthorities;

        for (Role role :  user.getRoles()){
            userAuthorities.add(new SimpleGrantedAuthority(role.getRole_name()));    // SimpleGrantedAuthority can be created from role Strings
        }

        return userAuthorities;
    }

    @Transactional
    public User getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    @Transactional
    public User getUserByID(Integer user_id) {
        return userDao.getUserByID(user_id);
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Transactional
    public Integer createNewUser(User user, boolean is_admin) {
        return userDao.createNewUser(user, is_admin, roleDao);
    }

    @Transactional
    public void updateUserStatus(Integer user_id) {
        userDao.updateUserStatus(user_id);
    }
}
