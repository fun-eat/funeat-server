package com.funeat.member.dto;

import com.funeat.product.domain.Product;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;

import java.time.LocalDateTime;
import java.util.List;

public record MemberBookmarkRecipeDto(
        Long id,
        String title,
        String image,
        String content,
        Boolean favorite,
        MemberResponse author,
        MemberBookmarkRecipeProductsDto products,
        LocalDateTime createdAt
) {

    private MemberBookmarkRecipeDto(final Long id, final String title, final String content, final Boolean favorite,
                                    final MemberResponse author, final MemberBookmarkRecipeProductsDto products,
                                    final LocalDateTime createdAt) {
        this(id, title, null, content, favorite, author, products, createdAt);
    }

    public static MemberBookmarkRecipeDto toDto(final Recipe recipe, final List<RecipeImage> findRecipeImages,
                                                final List<Product> recipeInProducts, final Boolean isFavorite) {
        final MemberResponse author = MemberResponse.toResponse(recipe.getMember());
        final MemberBookmarkRecipeProductsDto products = MemberBookmarkRecipeProductsDto.toDto(recipeInProducts);

        if (findRecipeImages.isEmpty()) {
            return new MemberBookmarkRecipeDto(recipe.getId(), recipe.getTitle(), recipe.getContent(), isFavorite,
                    author, products, recipe.getCreatedAt()
            );
        }
        return new MemberBookmarkRecipeDto(recipe.getId(), recipe.getTitle(), findRecipeImages.get(0).getImage(),
                recipe.getContent(), isFavorite, author, products, recipe.getCreatedAt()
        );
    }
}
