package com.funeat.recipe.dto;

import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.time.LocalDateTime;
import java.util.List;

public class RankingRecipeDto {

    private final Long id;
    private final String image;
    private final String title;
    private final String author;
    private final Boolean favorite;
    private final LocalDateTime createdAt;

    public RankingRecipeDto(final Long id, final String image, final String title, final String author,
                            final Boolean favorite, final LocalDateTime createdAt) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.author = author;
        this.favorite = favorite;
        this.createdAt = createdAt;
    }

    public static RankingRecipeDto toDto(final Recipe recipe, final List<RecipeImage> images, final Boolean favorite) {
        if (images.isEmpty()) {
            return new RankingRecipeDto(recipe.getId(), null, recipe.getTitle(),
                    recipe.getMember().getNickname(), favorite, recipe.getCreatedAt());
        }
        return new RankingRecipeDto(recipe.getId(), images.get(0).getImage(), recipe.getTitle(),
                recipe.getMember().getNickname(), favorite, recipe.getCreatedAt());
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

    public String getAuthor() {
        return author;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
