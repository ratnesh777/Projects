
package com.mars.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mars.mapper.UserMapper;
import com.mars.models.User;
import com.mars.repository.UserRepository;
import com.mars.repository.entity.UserEntity;




@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        final UserEntity user = userRepo.findByEmail(email);
        if (user == null)
        {
            throw new UsernameNotFoundException("email not found");
        }

        return user;
    }

}
