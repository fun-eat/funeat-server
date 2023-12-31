package com.funeat.recipe.dto;

import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.time.LocalDateTime;
import java.util.List;

public class RankingRecipeDto {

    private final Long id;
    private final String image;
    private final String title;
    private final RecipeAuthorDto author;
    private final Long favoriteCount;
    private final LocalDateTime createdAt;

    public RankingRecipeDto(final Long id, final String image, final String title, final RecipeAuthorDto author,
                            final Long favoriteCount, final LocalDateTime createdAt) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.author = author;
        this.favoriteCount = favoriteCount;
        this.createdAt = createdAt;
    }

    public static RankingRecipeDto toDto(final Recipe recipe, final List<RecipeImage> images,
                                         final RecipeAuthorDto author) {
        if (images.isEmpty()) {
            return new RankingRecipeDto(recipe.getId(), null, recipe.getTitle(), author,
                    recipe.getFavoriteCount(), recipe.getCreatedAt());
        }
        return new RankingRecipeDto(recipe.getId(), images.get(0).getImage(), recipe.getTitle(), author,
                recipe.getFavoriteCount(), recipe.getCreatedAt());
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

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
