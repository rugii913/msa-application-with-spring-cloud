package com.example.catalogservice.jpa;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@NoArgsConstructor(force = true)
@Entity
@Table(name = "catalog")
public class CatalogEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 120, unique = true)
    public final String productId;

    @Column(nullable = false)
    public final String productName;

    @Column(nullable = false)
    public final int stock;

    @Column(nullable = false)
    public final int unitPrice;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    public final LocalDateTime createdAt;
}
