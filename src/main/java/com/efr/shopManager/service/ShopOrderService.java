package com.efr.shopManager.service;

import com.efr.shopManager.exceptions.OrderNotFoundException;
import com.efr.shopManager.service.interfaces.ShopOrderInterface;
import com.efr.shopManager.model.ShopOrder;
import com.efr.shopManager.repository.ShopOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopOrderService implements ShopOrderInterface {

    private final ShopOrderRepository shopOrderRepository;

    @Autowired
    public ShopOrderService(ShopOrderRepository shopOrderRepository) {
        this.shopOrderRepository = shopOrderRepository;
    }

    @Override
    public List<ShopOrder> getAllOrders() {
        return shopOrderRepository.findAll();
    }

    @Override
    public Optional<ShopOrder> getOrderById(Long id) {
        return shopOrderRepository.findById(id);
    }

    @Override
    public ShopOrder createOrder(ShopOrder order) {
        return shopOrderRepository.save(order);
    }

    @Transactional
    @Override
    public ShopOrder updateOrder(Long id, ShopOrder orderDetails) {
        return shopOrderRepository.findById(id).map(order -> {
            order.setStatus(orderDetails.getStatus());
            order.setProducts(orderDetails.getProducts());
            return shopOrderRepository.save(order);
        }).orElseThrow(() -> new OrderNotFoundException("Заказ с id " + id + " не найден"));
    }

    @Override
    public void deleteOrder(Long id) {
        shopOrderRepository.deleteById(id);
    }
}
