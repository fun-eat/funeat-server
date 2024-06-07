package com.funeat.recipe.dto;

import jakarta.validation.constraints.NotNull;

public record RecipeBookmarkRequest (
        @NotNull(message = "북마크를 확인해주세요")
        Boolean bookmark
) {
}
