package com.example.catalogservice.jpa;

import org.springframework.data.repository.ListCrudRepository;

public interface CatalogRepository extends ListCrudRepository<CatalogEntity, Long> {

    CatalogEntity findByProductId(String productId);
}
