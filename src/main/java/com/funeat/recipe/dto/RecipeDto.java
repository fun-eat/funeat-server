package com.funeat.recipe.dto;

import com.funeat.product.domain.Product;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.time.LocalDateTime;
import java.util.List;

public class RecipeDto {

    private final Long id;
    private final String image;
    private final String title;
    private final String content;
    private final RecipeAuthorDto author;
    private final List<ProductRecipeDto> products;
    private final Boolean favorite;
    private final LocalDateTime createdAt;

    public RecipeDto(final Long id, final String image, final String title, final String content,
                     final RecipeAuthorDto author, final List<ProductRecipeDto> products, final Boolean favorite,
                     final LocalDateTime createdAt) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.content = content;
        this.author = author;
        this.products = products;
        this.favorite = favorite;
        this.createdAt = createdAt;
    }

    public static RecipeDto toDto(final Recipe recipe, final List<RecipeImage> recipeImages,
                                  final List<Product> products, final Boolean favorite) {
        final RecipeAuthorDto authorDto = RecipeAuthorDto.toDto(recipe.getMember());
        final List<ProductRecipeDto> productDtos = products.stream()
                .map(ProductRecipeDto::toDto)
                .toList();

        if (recipeImages.isEmpty()) {
            return new RecipeDto(recipe.getId(), null, recipe.getTitle(), recipe.getContent(), authorDto,
                    productDtos, favorite, recipe.getCreatedAt());
        }
        return new RecipeDto(recipe.getId(), recipeImages.get(0).getImage(), recipe.getTitle(), recipe.getContent(),
                authorDto, productDtos, favorite, recipe.getCreatedAt());
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

    public String getContent() {
        return content;
    }

    public RecipeAuthorDto getAuthor() {
        return author;
    }

    public List<ProductRecipeDto> getProducts() {
        return products;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
