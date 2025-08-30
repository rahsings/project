package com.fastfashion.domain;

import jakarta.persistence.*;

@Entity
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private Product product;

    @Version
    private long version;

    private int available;
    private int reserved;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public long getVersion() { return version; }
    public void setVersion(long version) { this.version = version; }
    public int getAvailable() { return available; }
    public void setAvailable(int available) { this.available = available; }
    public int getReserved() { return reserved; }
    public void setReserved(int reserved) { this.reserved = reserved; }
}
