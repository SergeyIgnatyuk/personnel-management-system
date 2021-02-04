package com.security;

import com.exceptions.ResourceNotFoundException;
import com.model.User;
import com.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserDetailsService} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        LOGGER.debug("Trying to find a user from data base with {} and return user details", email);

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("User with email: " + email + " Not Found!"));
        return SecurityUser.fromUser(user);
    }
}
