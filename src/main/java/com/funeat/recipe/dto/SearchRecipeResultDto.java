package com.funeat.recipe.dto;

import com.funeat.product.domain.Product;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.time.LocalDateTime;
import java.util.List;

public class SearchRecipeResultDto {

    private final Long id;
    private final String image;
    private final String title;
    private final RecipeAuthorDto author;
    private final List<DetailProductRecipeDto> products;
    private final Long favoriteCount;
    private final LocalDateTime createdAt;

    public SearchRecipeResultDto(final Long id, final String image, final String title, final RecipeAuthorDto author,
                                 final List<DetailProductRecipeDto> products, final Long favoriteCount,
                                 final LocalDateTime createdAt) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.author = author;
        this.products = products;
        this.favoriteCount = favoriteCount;
        this.createdAt = createdAt;
    }

    public static SearchRecipeResultDto toDto(final Recipe recipe, final List<RecipeImage> images,
                                              final List<Product> products) {
        final List<DetailProductRecipeDto> productRecipes = products.stream()
                .map(DetailProductRecipeDto::toDto)
                .toList();

        if (images.isEmpty()) {
            return new SearchRecipeResultDto(recipe.getId(), null, recipe.getTitle(),
                    RecipeAuthorDto.toDto(recipe.getMember()), productRecipes, recipe.getFavoriteCount(),
                    recipe.getCreatedAt());
        }
        return new SearchRecipeResultDto(recipe.getId(), images.get(0).getImage(), recipe.getTitle(),
                RecipeAuthorDto.toDto(recipe.getMember()), productRecipes, recipe.getFavoriteCount(),
                recipe.getCreatedAt());
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public RecipeAuthorDto getAuthor() {
        return author;
    }

    public List<DetailProductRecipeDto> getProducts() {
        return products;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
