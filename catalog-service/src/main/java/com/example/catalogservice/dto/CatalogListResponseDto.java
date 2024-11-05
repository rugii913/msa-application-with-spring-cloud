package com.example.catalogservice.dto;

import java.util.List;

public record CatalogListResponseDto(
        List<CatalogListItemDto> catalogList
) {}
