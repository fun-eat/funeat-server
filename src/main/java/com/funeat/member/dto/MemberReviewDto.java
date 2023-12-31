package com.funeat.member.dto;

import com.funeat.review.domain.Review;

public class MemberReviewDto {

    private final Long reviewId;
    private final Long productId;
    private final String categoryType;
    private final String productName;
    private final String content;
    private final Long rating;
    private final Long favoriteCount;

    private MemberReviewDto(final Long reviewId, final Long productId, final String categoryType,
                            final String productName, final String content,
                            final Long rating, final Long favoriteCount) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.categoryType = categoryType;
        this.productName = productName;
        this.content = content;
        this.rating = rating;
        this.favoriteCount = favoriteCount;
    }

    public static MemberReviewDto toDto(final Review review) {
        return new MemberReviewDto(
                review.getId(),
                review.getProduct().getId(),
                review.getProduct().getCategory().getType().getName(),
                review.getProduct().getName(),
                review.getContent(),
                review.getRating(),
                review.getFavoriteCount()
        );
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getContent() {
        return content;
    }

    public Long getRating() {
        return rating;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public String getCategoryType() {
        return categoryType;
    }
}
