package com.funeat.review.dto;

import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.dto.TagDto;
import java.util.List;

public record RankingReviewDto(
        Long id,
        String userName,
        String profileImage,
        Long productId,
        String productName,
        String content,
        String image,
        Long rating,
        Boolean rebuy,
        Long favoriteCount,
        Boolean favorite,
        List<TagDto> tags
) {

    public static RankingReviewDto toDto(final Review review, final Boolean favorite) {
        final List<TagDto> tagDtos = review.getReviewTags().stream()
                .map(ReviewTag::getTag)
                .map(TagDto::toDto)
                .toList();

        return new RankingReviewDto(
                review.getId(),
                review.getMember().getNickname(),
                review.getMember().getProfileImage(),
                review.getProduct().getId(),
                review.getProduct().getName(),
                review.getContent(),
                review.getImage(),
                review.getRating(),
                review.getReBuy(),
                review.getFavoriteCount(),
                favorite,
                tagDtos
        );
    }
}
