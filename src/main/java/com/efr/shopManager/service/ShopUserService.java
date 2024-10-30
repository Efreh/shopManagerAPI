package com.efr.shopManager.service;

import com.efr.shopManager.exceptions.UserNotFoundException;
import com.efr.shopManager.service.interfaces.ShopUserInterface;
import com.efr.shopManager.model.ShopUser;
import com.efr.shopManager.repository.ShopUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopUserService implements ShopUserInterface {

    private final ShopUserRepository shopUserRepository;

    @Autowired
    public ShopUserService(ShopUserRepository shopUserRepository) {
        this.shopUserRepository = shopUserRepository;
    }

    @Override
    public List<ShopUser> getAllUsers() {
        return shopUserRepository.findAll();
    }

    @Override
    public Optional<ShopUser> getUserById(Long id) {
        Optional<ShopUser> userFromRepository = shopUserRepository.findById(id);
        if (userFromRepository.isEmpty()) {
            throw new UserNotFoundException("Пользователь с id " + id + " не найден");
        } else return userFromRepository;
    }

    @Override
    public ShopUser createUser(ShopUser user) {
        return shopUserRepository.save(user);
    }

    @Override
    @Transactional
    public ShopUser updateUser(Long id, ShopUser userDetails) {
        return shopUserRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return shopUserRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + " не найден"));
    }

    @Override
    public void deleteUser(Long id) {
        shopUserRepository.deleteById(id);
    }
}
