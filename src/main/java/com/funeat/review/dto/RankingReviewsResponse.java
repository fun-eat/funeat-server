package com.funeat.review.dto;

import java.util.List;

public record RankingReviewsResponse(
        List<RankingReviewDto> reviews
) {

    public static RankingReviewsResponse toResponse(final List<RankingReviewDto> reviews) {
        return new RankingReviewsResponse(reviews);
    }
}
