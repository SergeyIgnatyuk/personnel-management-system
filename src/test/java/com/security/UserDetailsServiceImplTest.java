package com.security;

import com.model.Role;
import com.model.Status;
import com.model.User;
import com.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
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
public class UserDetailsServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @TestConfiguration
    public static class UserDetailsServiceImplTestContextConfiguration {
        @Bean
        protected UserRepository userRepository() {
            return mock(UserRepository.class);
        }

        @Bean
        protected UserDetailsService userDetailsService() {
            return new UserDetailsServiceImpl(userRepository());
        }

    }

    @Test
    public void whenLoadUserByUsername_findByEmailShouldBeCalled_andReturnUserDetails() {
        User user = User.builder()
                .id(1L)
                .firstName("Sergey")
                .lastName("Sergeev")
                .email("sergeev89@gmail.com")
                .password("12345678")
                .role(Role.ADMIN)
                .status(Status.ACTIVE)
                .build();

        when(userRepository.findByEmail("sergeev89@gmail.com")).thenReturn(java.util.Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("sergeev89@gmail.com");

        verify(userRepository, times(1)).findByEmail("sergeev89@gmail.com");

        Assert.assertEquals(user.getEmail(), userDetails.getUsername());
        Assert.assertEquals(user.getPassword(), userDetails.getPassword());
        Assert.assertEquals(user.getRole().getAuthorities(), userDetails.getAuthorities());
        Assert.assertTrue(userDetails.isAccountNonExpired());
        Assert.assertTrue(userDetails.isAccountNonLocked());
        Assert.assertTrue(userDetails.isCredentialsNonExpired());
        Assert.assertTrue(userDetails.isEnabled());
    }
}