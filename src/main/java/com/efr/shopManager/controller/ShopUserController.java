package com.efr.shopManager.controller;

import com.efr.shopManager.exceptions.UserNotFoundException;
import com.efr.shopManager.model.ShopUser;
import com.efr.shopManager.service.interfaces.ShopUserInterface;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ShopUserController {

    private final ShopUserInterface userService;

    @Autowired
    public ShopUserController(ShopUserInterface userService) {
        this.userService = userService;
    }

    @GetMapping
    @JsonView(ShopUser.SummaryView.class)
    public List<ShopUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @JsonView(ShopUser.DetailView.class)
    public ShopUser getUserById(@PathVariable Long id) {
        return userService.getUserById(id).get();
    }

    @PostMapping
    public ResponseEntity<ShopUser> createUser(@RequestBody ShopUser user){
        ShopUser createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopUser> updateUser(@PathVariable Long id, @RequestBody ShopUser userDetails){
        ShopUser updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
