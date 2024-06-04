package com.funeat.member.dto;

import com.funeat.product.domain.Product;

public record MemberBookmarkRecipeProductDto(
        Long id,
        String name,
        Long price,
        String image,
        Double averageRating
) {

    public static MemberBookmarkRecipeProductDto toDto(final Product product) {
        return new MemberBookmarkRecipeProductDto(product.getId(), product.getName(), product.getPrice(),
                product.getImage(), product.getAverageRating());
    }
}
