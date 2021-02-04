package com.security;

import com.model.User;
import com.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

/**
 * Integrations tests of {@link UserDetailsServiceImpl}.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@RunWith(SpringRunner.class)
class UserDetailsServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @TestConfiguration
    static class UserDetailsServiceImplTestContextConfiguration {

        @Bean
        protected UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        protected UserDetailsService userDetailsService() {
            return new UserDetailsServiceImpl(userRepository());
        }
    }

    //@Test
    void whenLoadUserByUsername_findByEmailShouldBeCalled() {
        when(userRepository.findByEmail("sergeev89@gmail.com")).thenReturn(java.util.Optional.of(new User()));
        userDetailsService.loadUserByUsername("sergeev89@gmail.com");
        verify(userRepository, times(1)).findByEmail("sergeev89@gmail.com");
    }
}