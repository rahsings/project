package com.fastfashion.web;

import com.fastfashion.domain.Product;
import com.fastfashion.domain.Shop;
import com.fastfashion.repository.ProductRepository;
import com.fastfashion.repository.ShopRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShopController {
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    public ShopController(ShopRepository shopRepository, ProductRepository productRepository) {
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/shops")
    @Cacheable("shops")
    public List<Shop> shops(@RequestParam(defaultValue = "Bangalore") String city) {
        return shopRepository.findByCity(city);
    }

    @GetMapping("/shops/{id}/products")
    @Cacheable("productsByShop")
    public List<Product> products(@PathVariable Long id) {
        return productRepository.findByShopId(id);
    }
}
