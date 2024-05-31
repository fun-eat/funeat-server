package com.funeat.member.dto;

import java.time.LocalDateTime;

public record MemberBookmarkRecipeDto(
        Long id,
        String title,
        String image,
        String content,
        Boolean favorite,
        MemberResponse author,
        MemberBookmarkRecipeProductDto products,
        LocalDateTime createdAt
) {
}
