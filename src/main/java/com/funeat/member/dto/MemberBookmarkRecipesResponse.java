package com.funeat.member.dto;

import com.funeat.common.dto.PageDto;

import java.util.List;

public record MemberBookmarkRecipesResponse(
        PageDto page,
        List<MemberBookmarkRecipeDto> recipes
) {

    public static MemberBookmarkRecipesResponse toResponse(final PageDto page,
                                                           final List<MemberBookmarkRecipeDto> recipes) {
        return new MemberBookmarkRecipesResponse(page, recipes);
    }
}
