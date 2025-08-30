package com.fastfashion.service;

import com.fastfashion.domain.Inventory;
import com.fastfashion.repository.InventoryRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public void reserve(Long productId, int qty) {
        int attempts = 0;
        while (true) {
            attempts++;
            Inventory inv = inventoryRepository.findByProductId(productId);
            if (inv == null || inv.getAvailable() < qty) {
                throw new IllegalStateException("Insufficient stock");
            }
            inv.setAvailable(inv.getAvailable() - qty);
            inv.setReserved(inv.getReserved() + qty);
            try {
                inventoryRepository.save(inv);
                return;
            } catch (OptimisticLockException e) {
                if (attempts >= 3) throw new IllegalStateException("Inventory conflict, try again");
                try { Thread.sleep(50L * attempts); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
    }

    @Transactional
    public void release(Long productId, int qty) {
        Inventory inv = inventoryRepository.findByProductId(productId);
        if (inv == null) return;
        inv.setReserved(Math.max(0, inv.getReserved() - qty));
        inv.setAvailable(inv.getAvailable() + qty);
        inventoryRepository.save(inv);
    }
}
