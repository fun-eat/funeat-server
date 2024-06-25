package com.funeat.recipe.dto;

import jakarta.validation.constraints.NotNull;

public record RecipeBookmarkRequest (
        @NotNull(message = "레시피 저장 기능을 확인해주세요")
        Boolean bookmark
) {
}
