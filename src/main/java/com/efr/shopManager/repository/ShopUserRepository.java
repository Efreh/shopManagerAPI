package com.efr.shopManager.repository;

import com.efr.shopManager.model.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopUserRepository extends JpaRepository<ShopUser,Long> {
}
