package com.example.catalogservice.service;

import com.example.catalogservice.dto.CatalogListItemDto;
import com.example.catalogservice.dto.CatalogListResponseDto;
import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public CatalogListResponseDto findAllCatalogs() {
        return new CatalogListResponseDto(catalogRepository.findAll().stream().map(CatalogListItemDto::from).toList());
    }
}
