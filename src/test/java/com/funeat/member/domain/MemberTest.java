package com.funeat.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.member.exception.MemberException.MemberUpdateException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class MemberTest {

    @Nested
    class modifyProfile_성공_테스트 {

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
            assertSoftly(soft -> {
                soft.assertThat(actualNickname)
                        .isEqualTo(expectedNickname);
                soft.assertThat(actualProfileImage)
                        .isEqualTo(expectedProfileImage);
            });
        }
    }

    @Nested
    class modifyProfile_실패_테스트 {

        @Test
        void 사용자의_닉네임_변경_값이_null이면_예외가_발생한다() {
            // given
            final var member = new Member("test", "http://www.before.com", "1");

            final var expectedProfileImage = "http://www.after.com";

            // when & then
            assertThatThrownBy(() -> member.modifyProfile(null, expectedProfileImage))
                    .isInstanceOf(MemberUpdateException.class);
        }

        @Test
        void 사용자의_프로필_이미지_변경_값이_null이면_예외가_발생한다() {
            // given
            final var member = new Member("test", "http://www.before.com", "1");

            final var expectedNickname = "after";

            // when & then
            assertThatThrownBy(() -> member.modifyProfile(expectedNickname, null))
                    .isInstanceOf(MemberUpdateException.class);
        }
    }

    @Nested
    class isGuest_성공_테스트 {

        @Test
        void 비로그인_사용자는_비로그인_사용자로_식별할_수_있다() {
            // given
            final var member = Member.createGuest();

            // when & then
            assertThat(member.isGuest()).isTrue();
        }

        @Test
        void 로그인_사용자는_비로그인_사용자로_식별할_수_없다() {
            // given
            final var member = new Member("test", "http://www.before.com", "1");

            // when & then
            assertThat(member.isGuest()).isFalse();
        }
    }
}
