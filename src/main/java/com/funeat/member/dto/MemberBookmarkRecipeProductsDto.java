package com.funeat.member.dto;

import com.funeat.product.domain.Product;

import java.util.List;

public record MemberBookmarkRecipeProductsDto(
        List<MemberBookmarkRecipeProductDto> products
) {

    public static MemberBookmarkRecipeProductsDto toDto(final List<Product> recipeInProducts) {
        final List<MemberBookmarkRecipeProductDto> products = recipeInProducts.stream()
                .map(MemberBookmarkRecipeProductDto::toDto)
                .toList();

        return new MemberBookmarkRecipeProductsDto(products);
    }
}
