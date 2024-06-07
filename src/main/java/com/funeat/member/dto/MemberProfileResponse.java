package com.funeat.member.dto;

import com.funeat.member.domain.Member;

public class MemberProfileResponse {

    private final String nickname;
    private final String profileImage;
    private final Long reviewCount;
    private final Long recipeCount;

    public MemberProfileResponse(final String nickname, final String profileImage,
                                 final Long reviewCount, final Long recipeCount) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.reviewCount = reviewCount;
        this.recipeCount = recipeCount;
    }

    public static MemberProfileResponse toResponse(final Member member, final Long reviewCount, final Long recipeCount) {
        return new MemberProfileResponse(member.getNickname(), member.getProfileImage(), reviewCount, recipeCount);
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public Long getRecipeCount() {
        return recipeCount;
    }
}
