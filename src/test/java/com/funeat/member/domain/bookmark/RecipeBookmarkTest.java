package com.funeat.member.domain.bookmark;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.RecipeFixture.레시피_생성;
import static org.assertj.core.api.Assertions.assertThat;

class RecipeBookmarkTest {

    @Nested
    class updateBookmark_성공_테스트 {

        @Test
        void 기존_false_신규_false인_경우_bookmark는_false_유지() {
            // given
            final var member = 멤버_멤버1_생성();
            final var recipe = 레시피_생성(member);
            final var recipeBookmark = RecipeBookmark.create(member, recipe, false);

            // when
            recipeBookmark.updateBookmark(false);

            // then
            assertThat(recipeBookmark.getBookmark()).isFalse();
        }

        @Test
        void 기존_false_신규_true인_경우_bookmark는_true로_변경() {
            // given
            final var member = 멤버_멤버1_생성();
            final var recipe = 레시피_생성(member);
            final var recipeBookmark = RecipeBookmark.create(member, recipe, false);

            // when
            recipeBookmark.updateBookmark(true);

            // then
            assertThat(recipeBookmark.getBookmark()).isTrue();
        }

        @Test
        void 기존_true_신규_true인_경우_bookmark는_true_유지() {
            // given
            final var member = 멤버_멤버1_생성();
            final var recipe = 레시피_생성(member);
            final var recipeBookmark = RecipeBookmark.create(member, recipe, true);

            // when
            recipeBookmark.updateBookmark(true);

            // then
            assertThat(recipeBookmark.getBookmark()).isTrue();
        }

        @Test
        void 기존_true_신규_false인_경우_bookmark는_false로_변경() {
            // given
            final var member = 멤버_멤버1_생성();
            final var recipe = 레시피_생성(member);
            final var recipeBookmark = RecipeBookmark.create(member, recipe, true);

            // when
            recipeBookmark.updateBookmark(false);

            // then
            assertThat(recipeBookmark.getBookmark()).isFalse();
        }
    }
}
