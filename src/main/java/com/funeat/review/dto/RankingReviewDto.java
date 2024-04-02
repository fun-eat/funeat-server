package com.funeat.review.dto;

import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.dto.TagDto;
import java.util.List;

public class RankingReviewDto {

    private final Long reviewId;
    private final Long productId;
    private final String categoryType;
    private final String productName;
    private final String content;
    private final String image;
    private final List<TagDto> tags;

    private RankingReviewDto(final Long reviewId, final Long productId, final String categoryType,
                             final String productName, final String content, final String image,
                             final List<TagDto> tags) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.categoryType = categoryType;
        this.productName = productName;
        this.content = content;
        this.image = image;
        this.tags = tags;
    }

    public static RankingReviewDto toDto(final Review review) {
        final List<TagDto> tagDtos = review.getReviewTags().stream()
                .map(ReviewTag::getTag)
                .map(TagDto::toDto)
                .toList();

        return new RankingReviewDto(
                review.getId(),
                review.getProduct().getId(),
                review.getProduct().getCategory().getType().getName(),
                review.getProduct().getName(),
                review.getContent(),
                review.getImage(),
                tagDtos
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

    public String getCategoryType() {
        return categoryType;
    }

    public String getImage() {
        return image;
    }

    public List<TagDto> getTags() {
        return tags;
    }
}
