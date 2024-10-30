package com.efr.shopManager.service.interfaces;

import com.efr.shopManager.model.ShopOrder;

import java.util.List;
import java.util.Optional;

public interface ShopOrderInterface {

    List<ShopOrder> getAllOrders();

    Optional<ShopOrder> getOrderById(Long id);

    ShopOrder createOrder(ShopOrder order);

    ShopOrder updateOrder(Long id, ShopOrder orderDetails);

    void deleteOrder(Long id);
}
