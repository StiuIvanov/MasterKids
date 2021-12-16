package com.example.demo.service.impl;

import com.example.demo.model.entity.ParentEntity;
import com.example.demo.repository.ParentRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterKidsUserServiceImpl implements UserDetailsService {

    private ParentRepository parentRepository;

    public MasterKidsUserServiceImpl(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ParentEntity parentEntity = parentRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username +" not found!"));

        return mapToUserDetails(parentEntity);
    }


    private static UserDetails mapToUserDetails(ParentEntity parentEntity){

        List<GrantedAuthority> grantedAuthorities =
                parentEntity
                        .getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name()))
                .collect(Collectors.toList());

        return new User(
                parentEntity.getUsername(),
                parentEntity.getPassword(),
                grantedAuthorities
        );
    }
}
