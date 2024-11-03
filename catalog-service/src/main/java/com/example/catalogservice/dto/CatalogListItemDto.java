package com.example.catalogservice.dto;

import com.example.catalogservice.jpa.CatalogEntity;

import java.time.LocalDateTime;

public record CatalogListItemDto(
        String productId,
        String productName,
        int unitPrice,
        int stock,
        LocalDateTime createAt
) {

    public static CatalogListItemDto from(CatalogEntity catalogEntity) {
        return new CatalogListItemDto(
                catalogEntity.productId,
                catalogEntity.productName,
                catalogEntity.unitPrice,
                catalogEntity.stock,
                catalogEntity.createdAt
        );
    }
}
