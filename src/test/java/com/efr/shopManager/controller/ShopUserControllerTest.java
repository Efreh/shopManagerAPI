package com.efr.shopManager.controller;

import com.efr.shopManager.exceptions.UserNotFoundException;
import com.efr.shopManager.model.ShopUser;
import com.efr.shopManager.service.interfaces.ShopUserInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShopUserController.class)
class ShopUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopUserInterface userService;

    @Autowired
    private ObjectMapper objectMapper;

    private ShopUser user;

    @BeforeEach
    void setUp() {
        user = new ShopUser("Test User", "test@example.com");
        user.setId(1L);
    }

    @Test
    void getAllUsers_ShouldReturnUsersList_WithSummaryView() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(user.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(user.getName())))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())))
                .andExpect(jsonPath("$[0].shopOrders").doesNotExist());
    }

    @Test
    void getUserById_ShouldReturnUser_WithDetailView() throws Exception {
        Mockito.when(userService.getUserById(user.getId())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.shopOrders", is(empty())));
    }

    @Test
    void getUserById_UserNotFound_ShouldReturn404() throws Exception {
        Mockito.when(userService.getUserById(anyLong())).thenThrow(new UserNotFoundException("Пользователь не найден"));

        mockMvc.perform(get("/api/users/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("User Not Found")))
                .andExpect(jsonPath("$.message", is("Пользователь не найден")));
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        Mockito.when(userService.createUser(any(ShopUser.class))).thenReturn(user);

        ResultActions resultActions = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.shopOrders").isArray());
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        ShopUser updatedUser = new ShopUser("Updated User", "updated@example.com");
        updatedUser.setId(user.getId());

        Mockito.when(userService.updateUser(anyLong(), any(ShopUser.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedUser.getId().intValue())))
                .andExpect(jsonPath("$.name", is(updatedUser.getName())))
                .andExpect(jsonPath("$.email", is(updatedUser.getEmail())));
    }

    @Test
    void updateUser_UserNotFound_ShouldReturn404() throws Exception {
        Mockito.when(userService.updateUser(anyLong(), any(ShopUser.class))).thenThrow(new UserNotFoundException("Пользователь не найден"));

        mockMvc.perform(put("/api/users/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("User Not Found")))
                .andExpect(jsonPath("$.message", is("Пользователь не найден")));
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(user.getId());

        mockMvc.perform(delete("/api/users/{id}", user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_UserNotFound_ShouldReturn404() throws Exception {
        Mockito.doThrow(new UserNotFoundException("Пользователь не найден")).when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/api/users/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("User Not Found")))
                .andExpect(jsonPath("$.message", is("Пользователь не найден")));
    }
}