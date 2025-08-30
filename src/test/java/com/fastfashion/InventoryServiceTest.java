package com.fastfashion;

import com.fastfashion.domain.Inventory;
import com.fastfashion.domain.Product;
import com.fastfashion.repository.InventoryRepository;
import com.fastfashion.repository.ProductRepository;
import com.fastfashion.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InventoryServiceTest {
    @Autowired InventoryService inventoryService;
    @Autowired InventoryRepository inventoryRepository;
    @Autowired ProductRepository productRepository;

    @Test
    void reserveAndReleaseAdjustsQuantities() {
        Product p = productRepository.findAll().get(0);
        Inventory invBefore = inventoryRepository.findByProductId(p.getId());
        int avail = invBefore.getAvailable();
        int res = invBefore.getReserved();

        inventoryService.reserve(p.getId(), 1);
        Inventory invAfterReserve = inventoryRepository.findByProductId(p.getId());
        assertThat(invAfterReserve.getAvailable()).isEqualTo(avail - 1);
        assertThat(invAfterReserve.getReserved()).isEqualTo(res + 1);

        inventoryService.release(p.getId(), 1);
        Inventory invAfterRelease = inventoryRepository.findByProductId(p.getId());
        assertThat(invAfterRelease.getAvailable()).isEqualTo(avail);
        assertThat(invAfterRelease.getReserved()).isEqualTo(res);
    }
}
