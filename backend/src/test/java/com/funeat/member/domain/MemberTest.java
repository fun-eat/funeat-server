package com.funeat.member.domain;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class MemberTest {

    @Test
    void 사용자의_닉네임과_이미지_주소를_변경할_수_있다() {
        // given
        final var member = new Member("before", "http://www.before.com", "1");
        final var expectedNickname = "after";
        final var expectedProfileImage = "http://www.after.com";

        // when
        member.modifyProfile(expectedNickname, expectedProfileImage);
        final var actualNickname = member.getNickname();
        final var actualProfileImage = member.getProfileImage();

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(actualNickname).isEqualTo(expectedNickname);
            softAssertions.assertThat(actualProfileImage).isEqualTo(expectedProfileImage);
        });
    }
}
