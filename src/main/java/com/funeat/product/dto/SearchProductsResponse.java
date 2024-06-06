package com.funeat.product.dto;

import java.util.List;

public record SearchProductsResponse(
        boolean hasNext,
        List<SearchProductDto> products
) {

    public static SearchProductsResponse toResponse(final boolean hasNext, final List<SearchProductDto> products) {
        return new SearchProductsResponse(hasNext, products);
    }
}
