package com.funeat.recipe.dto;

import java.util.List;

public class SearchRecipeResultsResponse {

    private final boolean hasNext;
    private final List<SearchRecipeResultDto> recipes;

    private SearchRecipeResultsResponse(final boolean hasNext, final List<SearchRecipeResultDto> recipes) {
        this.hasNext = hasNext;
        this.recipes = recipes;
    }

    public static SearchRecipeResultsResponse toResponse(final boolean hasNext,
                                                         final List<SearchRecipeResultDto> recipes) {
        return new SearchRecipeResultsResponse(hasNext, recipes);
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public List<SearchRecipeResultDto> getRecipes() {
        return recipes;
    }
}
