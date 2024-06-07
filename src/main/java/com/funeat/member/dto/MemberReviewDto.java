package com.funeat.member.dto;

import com.funeat.review.domain.Review;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.dto.TagDto;

import java.time.LocalDateTime;
import java.util.List;

public class MemberReviewDto {

    private final Long reviewId;
    private final Long productId;
    private final String productName;
    private final String content;
    private final Long rating;
    private final List<TagDto> tags;
    private final String image;
    private final LocalDateTime createdAt;

    private MemberReviewDto(final Long reviewId, final Long productId, final String productName,
                            final String content, final Long rating, final List<TagDto> tags, final String image,
                            final LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.productName = productName;
        this.content = content;
        this.rating = rating;
        this.tags = tags;
        this.image = image;
        this.createdAt = createdAt;
    }

    public static MemberReviewDto toDto(final Review review, final List<Tag> tags) {
        final List<TagDto> tagDtos = tags.stream()
                .map(TagDto::toDto)
                .toList();

        return new MemberReviewDto(
                review.getId(),
                review.getProduct().getId(),
                review.getProduct().getName(),
                review.getContent(),
                review.getRating(),
                tagDtos,
                review.getImage(),
                review.getCreatedAt()
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

    public List<TagDto> getTags() {
        return tags;
    }

    public String getImage() {
        return image;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
