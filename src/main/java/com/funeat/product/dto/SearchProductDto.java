package com.funeat.product.dto;

import com.funeat.product.domain.Product;

public record SearchProductDto(
        Long id,
        String name,
        Long price,
        String image,
        Double averageRating,
        String categoryType
) {

    public static SearchProductDto toDto(final Product product) {
        return new SearchProductDto(product.getId(), product.getName(), product.getPrice(), product.getImage(),
                product.getAverageRating(), product.getCategory().getType().getName());
    }
}
