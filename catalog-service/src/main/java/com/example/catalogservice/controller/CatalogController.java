package com.example.catalogservice.controller;

import com.example.catalogservice.dto.CatalogListResponseDto;
import com.example.catalogservice.service.CatalogService;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;

    public CatalogController(Environment env, CatalogService catalogService) {
        this.env = env;
        this.catalogService = catalogService;
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok(
                String.format("catalog-service works well on port %s", env.getProperty("local.server.port"))
        );
    }

    @GetMapping("/catalogs")
    public ResponseEntity<CatalogListResponseDto> findAllCatalogs() {
        return ResponseEntity.ok(
                catalogService.findAllCatalogs()
        );
    }
}
