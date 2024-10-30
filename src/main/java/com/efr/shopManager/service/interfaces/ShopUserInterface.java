package com.efr.shopManager.service.interfaces;

import com.efr.shopManager.model.ShopUser;

import java.util.List;
import java.util.Optional;

public interface ShopUserInterface {

    List<ShopUser> getAllUsers();

    Optional<ShopUser> getUserById(Long id);

    ShopUser createUser(ShopUser user);

    ShopUser updateUser(Long id, ShopUser userDetails);

    void deleteUser(Long id);
}
