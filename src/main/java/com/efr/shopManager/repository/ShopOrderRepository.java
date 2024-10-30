package com.efr.shopManager.repository;

import com.efr.shopManager.model.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderRepository extends JpaRepository<ShopOrder,Long> {
}
