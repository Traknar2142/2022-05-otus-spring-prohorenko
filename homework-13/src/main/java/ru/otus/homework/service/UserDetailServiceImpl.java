package ru.otus.homework.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.EntryUser;
import ru.otus.homework.repository.UserRepository;
import ru.otus.homework.utill.GrandedAuthorityUtil;

import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EntryUser entryUser = userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Set<GrantedAuthority> grantedAuthorities = GrandedAuthorityUtil.getAuthoritiesByRole(entryUser.getRole());

        return new User(entryUser.getLogin(), entryUser.getPassword(), grantedAuthorities);
    }
}
