package com.fastfashion.service;

import com.fastfashion.domain.*;
import com.fastfashion.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public DataSeeder(ShopRepository shopRepository, ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (shopRepository.count() > 0) return;
        Shop s1 = new Shop(); s1.setName("Urban Threads"); s1.setAddress("MG Road"); s1.setCity("Bangalore");
        Shop s2 = new Shop(); s2.setName("Elite Wear"); s2.setAddress("Indiranagar"); s2.setCity("Bangalore");
        shopRepository.save(s1); shopRepository.save(s2);

        Product p1 = new Product(); p1.setShop(s1); p1.setBrand("BrandX"); p1.setName("Tee"); p1.setDescription("Cotton"); p1.setSize("M"); p1.setColor("Black"); p1.setPriceCents(9999);
        Product p2 = new Product(); p2.setShop(s1); p2.setBrand("BrandY"); p2.setName("Jeans"); p2.setDescription("Slim"); p2.setSize("32"); p2.setColor("Blue"); p2.setPriceCents(24999);
        Product p3 = new Product(); p3.setShop(s2); p3.setBrand("BrandZ"); p3.setName("Dress"); p3.setDescription("Summer"); p3.setSize("S"); p3.setColor("Red"); p3.setPriceCents(34999);
        productRepository.save(p1); productRepository.save(p2); productRepository.save(p3);

        Inventory i1 = new Inventory(); i1.setProduct(p1); i1.setAvailable(10); i1.setReserved(0);
        Inventory i2 = new Inventory(); i2.setProduct(p2); i2.setAvailable(5); i2.setReserved(0);
        Inventory i3 = new Inventory(); i3.setProduct(p3); i3.setAvailable(3); i3.setReserved(0);
        inventoryRepository.save(i1); inventoryRepository.save(i2); inventoryRepository.save(i3);
    }
}
