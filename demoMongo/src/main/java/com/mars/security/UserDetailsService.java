package com.mars.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mars.mongo.repository.UserRepository;
import com.mars.mongo.repository.entity.User;




@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    
   

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        final User user = userRepo.findByEmail(email);
        if (user == null)
        {
            throw new UsernameNotFoundException("email not found");
        }

        return user;
    }

}
