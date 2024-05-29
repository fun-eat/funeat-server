package com.funeat.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.funeat.recipe.domain.Recipe;
import com.funeat.recipe.domain.RecipeImage;
import java.time.LocalDateTime;
import java.util.List;

public class MemberRecipeDto {

    private final Long id;
    private final String title;
    private final String content;
    private final MemberResponse author;
    private final LocalDateTime createdAt;
    private final String image;

    private MemberRecipeDto(final Long id, final String title, final String content, final MemberResponse author,
                            final LocalDateTime createdAt) {
        this(id, title, content, author, createdAt, null);
    }

    @JsonCreator
    private MemberRecipeDto(final Long id, final String title, final String content, final MemberResponse author,
                            final LocalDateTime createdAt, final String image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.image = image;
    }

    public static MemberRecipeDto toDto(final Recipe recipe, final List<RecipeImage> findRecipeImages) {
        final MemberResponse author = MemberResponse.toResponse(recipe.getMember());

        if (findRecipeImages.isEmpty()) {
            return new MemberRecipeDto(recipe.getId(), recipe.getTitle(), recipe.getContent(),author,
                    recipe.getCreatedAt());
        }
        return new MemberRecipeDto(recipe.getId(), recipe.getTitle(), recipe.getContent(), author,
                recipe.getCreatedAt(), findRecipeImages.get(0).getImage());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public MemberResponse getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getImage() {
        return image;
    }
}
