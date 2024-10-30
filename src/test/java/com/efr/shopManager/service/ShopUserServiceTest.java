package com.efr.shopManager.service;

import com.efr.shopManager.exceptions.UserNotFoundException;
import com.efr.shopManager.model.ShopUser;
import com.efr.shopManager.repository.ShopUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ShopUserServiceTest {

    @Mock
    private ShopUserRepository shopUserRepository;

    @InjectMocks
    private ShopUserService userService;

    private ShopUser user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new ShopUser("John Doe", "john.doe@example.com");
        user.setId(1L);
    }

    @Test
    void testGetUserById_UserExists() {
        when(shopUserRepository.findById(1L)).thenReturn(Optional.of(user));
        ShopUser foundUser = userService.getUserById(1L).orElse(null);
        assertEquals("John Doe", foundUser.getName());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(shopUserRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void testCreateUser() {
        when(shopUserRepository.save(user)).thenReturn(user);
        ShopUser createdUser = userService.createUser(user);
        assertEquals("John Doe", createdUser.getName());
        verify(shopUserRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        when(shopUserRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, user));
    }
}