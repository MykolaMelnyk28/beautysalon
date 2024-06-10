package com.beautysalon.core.services;

import com.beautysalon.core.dto.mapper.UserIdentityMapper;
import com.beautysalon.core.repository.UserRepository;
import com.beautysalon.core.entities.UserEntity;
import com.beautysalon.core.entities.UserRole;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserIdentityMapper userMapper;

    public UserService(
            UserRepository userRepository,
        PasswordEncoder passwordEncoder,
            UserIdentityMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public Page<UserEntity> getAll() {
        List<UserEntity> users = userRepository.findAll();
        Pageable pageable = PageRequest.of(0, 15, Sort.by(Sort.Order.asc("id")));
        return new PageImpl<>(users, pageable, users.size());
    }

    @Transactional
    public UserEntity createWithRoles(UserEntity user, Set<UserRole> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getAuthorities().clear();
        return appendRoles(user, roles);
    }

    public void assignAccessToAdminPanel(String username, boolean isAccess)
            throws UsernameNotFoundException {
        UserEntity user = getByUsernameOrThrow(username);
        final UserRole RSA = UserRole.ROLE_SYS_ADMIN;
        final Set<UserRole> roles = user.getAuthorities();
        final boolean isContainRoleSysAdmin = roles.contains(RSA);
        if (isAccess && !isContainRoleSysAdmin) {
            roles.add(RSA);
            userRepository.save(user);
        } else if(!isAccess && isContainRoleSysAdmin) {
            roles.remove(RSA);
            userRepository.save(user);
        }
    }

    @Transactional
    public UserEntity appendRoles(String username, Set<UserRole> roles)
            throws UsernameNotFoundException {
        return appendRoles(getByUsernameOrThrow(username), roles);
    }


    UserEntity appendRoles(UserEntity user, Set<UserRole> roles) {
        final Set<UserRole> userRoles = user.getAuthorities();
        userRoles.addAll(roles);
        return userRepository.save(user);
    }

    @Transactional
    public UserEntity removeRole(String username, Set<UserRole> roles)
            throws UsernameNotFoundException {
        return removeRole(getByUsernameOrThrow(username), roles);
    }

    UserEntity removeRole(UserEntity user, Set<UserRole> roles) {
        final Set<UserRole> userRoles = user.getAuthorities();
        userRoles.removeAll(roles);
        return userRepository.save(user);
    }

    public boolean existsUsername(String username) {
        return getByUsername(username).isPresent();
    }

    public UserEntity update(String username, UserEntity user) {
        UserEntity found = getByUsernameOrThrow(username);
        userMapper.transferDtoEntity(user, found);
        userRepository.save(found);
        return found;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
    }

    @Transactional()
    public Optional<UserEntity> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    UserEntity getByUsernameOrThrow(String username) {
        return getByUsername(username).orElseThrow(() ->
            new UsernameNotFoundException("Username not found")
        );
    }

    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
