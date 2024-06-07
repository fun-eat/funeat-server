package com.funeat.member.dto;

import com.funeat.member.domain.Member;

public class MemberResponse {

    private final String nickname;
    private final String profileImage;

    public MemberResponse(final String nickname, final String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public static MemberResponse toResponse(final Member member) {
        return new MemberResponse(member.getNickname(), member.getProfileImage());
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
