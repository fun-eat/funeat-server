package com.funeat.review.dto;

import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.tag.dto.TagDto;
import java.util.List;

public class RankingReviewDto {

    private final Long id;
    private final String userName;
    private final String profileImage;
    private final Long productId;
    private final String productName;
    private final String content;
    private final String image;
    private final Long rating;
    private final boolean rebuy;
    private final Long favoriteCount;
    private final boolean favorite;
    private final List<TagDto> tags;

    private RankingReviewDto(final Long id, final String userName, final String profileImage, final Long productId,
                             final String productName, final String content, final String image,
                             final Long rating, final boolean rebuy, final Long favoriteCount, final boolean favorite,
                             final List<TagDto> tags) {
        this.id = id;
        this.userName = userName;
        this.profileImage = profileImage;
        this.productId = productId;
        this.productName = productName;
        this.content = content;
        this.image = image;
        this.rating = rating;
        this.rebuy = rebuy;
        this.favoriteCount = favoriteCount;
        this.favorite = favorite;
        this.tags = tags;
    }

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

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
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

    public String getImage() {
        return image;
    }

    public Long getRating() {
        return rating;
    }

    public boolean isRebuy() {
        return rebuy;
    }

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public List<TagDto> getTags() {
        return tags;
    }
}
