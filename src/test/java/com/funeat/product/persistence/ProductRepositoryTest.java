package com.funeat.product.persistence;

import static com.funeat.fixture.CategoryFixture.카테고리_간편식사_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버1_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버2_생성;
import static com.funeat.fixture.MemberFixture.멤버_멤버3_생성;
import static com.funeat.fixture.ProductFixture.상품_망고빙수_가격5000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격1000원_평점3점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격2000원_평점4점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격3000원_평점5점_생성;
import static com.funeat.fixture.ProductFixture.상품_삼각김밥_가격4000원_평점2점_생성;
import static com.funeat.fixture.ProductFixture.상품_애플망고_가격3000원_평점5점_생성;
import static com.funeat.fixture.ReviewFixture.ReviewTagFixture.리뷰태그_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test1_평점1점_재구매X_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test3_평점3점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매O_생성;
import static com.funeat.fixture.ReviewFixture.리뷰_이미지test5_평점5점_재구매X_생성;
import static com.funeat.fixture.TagFixture.태그_맛있어요_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_단짠단짠_TASTE_생성;
import static com.funeat.fixture.TagFixture.태그_갓성비_PRICE_생성;
import static com.funeat.fixture.TagFixture.태그_간식_ETC_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.funeat.common.RepositoryTest;
import com.funeat.product.dto.ProductReviewCountDto;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

@SuppressWarnings("NonAsciiCharacters")
class ProductRepositoryTest extends RepositoryTest {

    @Nested
    class findAllByAverageRatingGreaterThan3_성공_테스트 {

        @Test
        void 평점이_3보다_큰_모든_상품들과_리뷰_수를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_삼각김밥_가격1000원_평점3점_생성(category);
            final var product2 = 상품_삼각김밥_가격2000원_평점4점_생성(category);
            final var product3 = 상품_삼각김밥_가격3000원_평점5점_생성(category);
            final var product4 = 상품_삼각김밥_가격4000원_평점2점_생성(category);
            복수_상품_저장(product1, product2, product3, product4);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            final var member3 = 멤버_멤버3_생성();
            복수_멤버_저장(member1, member2, member3);

            final var review1_1 = 리뷰_이미지test5_평점5점_재구매X_생성(member1, product1, 0L, LocalDateTime.now().minusDays(2L));
            final var review1_2 = 리뷰_이미지test5_평점5점_재구매X_생성(member2, product1, 0L, LocalDateTime.now().minusDays(3L));
            final var review2_1 = 리뷰_이미지test5_평점5점_재구매X_생성(member3, product2, 0L, LocalDateTime.now().minusDays(10L));
            final var review2_2 = 리뷰_이미지test5_평점5점_재구매X_생성(member1, product2, 0L, LocalDateTime.now().minusDays(1L));
            final var review2_3 = 리뷰_이미지test5_평점5점_재구매X_생성(member2, product2, 0L, LocalDateTime.now().minusDays(9L));
            final var review3_1 = 리뷰_이미지test5_평점5점_재구매X_생성(member1, product3, 0L, LocalDateTime.now().minusDays(8L));
            복수_리뷰_저장(review1_1, review1_2, review2_1, review2_2, review2_3, review3_1);

            final var productReviewCountDto1 = new ProductReviewCountDto(product2, 3L);
            final var productReviewCountDto2 = new ProductReviewCountDto(product3, 1L);
            final var expected = List.of(productReviewCountDto1, productReviewCountDto2);

            // when
            final var actual = productRepository.findAllByAverageRatingGreaterThan3();

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findAllByNameContainingFirst_성공_테스트 {

        @Test
        void 상품명에_검색어가_포함된_상품들을_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2);

            final var expected = List.of(product2, product1);

            // when
            final var actual = productRepository.findAllByNameContainingFirst("망고", PageRequest.of(0, 2));

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findAllByNameContaining_성공_테스트 {

        @Test
        void 상품명에_검색어가_포함된_상품들을_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            final var product3 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            final var product4 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2, product3, product4);

            final var expected = List.of(product2, product1);

            // when
            final var actual = productRepository.findAllByNameContaining("망고", 3L, PageRequest.of(0, 4));

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findAllWithReviewCountByNameContainingFirst_성공_테스트 {

        @Test
        void 상품명에_검색어가_포함된_상품들과_리뷰_수를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            복수_멤버_저장(member1, member2);

            final var review1_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product1, 0L);
            final var review1_2 = 리뷰_이미지test5_평점5점_재구매O_생성(member2, product1, 0L);
            final var review2_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product2, 0L);
            복수_리뷰_저장(review1_1, review1_2, review2_1);

            final var expectedDto1 = new ProductReviewCountDto(product1, 2L);
            final var expectedDto2 = new ProductReviewCountDto(product2, 1L);
            final var expected = List.of(expectedDto2, expectedDto1);

            // when
            final var actual = productRepository.findAllWithReviewCountByNameContainingFirst("망고", PageRequest.of(0, 2));

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class findAllWithReviewCountByNameContaining_성공_테스트 {

        @Test
        void 상품명에_검색어가_포함된_상품들과_리뷰_수를_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            final var product3 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            final var product4 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2, product3, product4);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            복수_멤버_저장(member1, member2);

            final var review1_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product1, 0L);
            final var review1_2 = 리뷰_이미지test5_평점5점_재구매O_생성(member2, product1, 0L);
            final var review2_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product2, 0L);
            복수_리뷰_저장(review1_1, review1_2, review2_1);

            final var expectedDto1 = new ProductReviewCountDto(product1, 2L);
            final var expectedDto2 = new ProductReviewCountDto(product2, 1L);
            final var expected = List.of(expectedDto2, expectedDto1);

            // when
            final var actual = productRepository.findAllWithReviewCountByNameContaining("망고", 3L, PageRequest.of(0, 4));

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }

    @Nested
    class searchProductsByTopTagsFirst_성공_테스트 {

        @Test
        void 특정_태그가_포함된_상품들을_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var 태그_맛있어요 = 태그_맛있어요_TASTE_생성();
            final var 태그_단짠단짠 = 태그_단짠단짠_TASTE_생성();
            final var 태그_갓성비 = 태그_갓성비_PRICE_생성();

            final var 태그1 = 단일_태그_저장(태그_맛있어요);
            final var 태그2 = 단일_태그_저장(태그_단짠단짠);
            final var 태그3 = 단일_태그_저장(태그_갓성비);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            final var product3 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            final var product4 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2, product3, product4);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            복수_멤버_저장(member1, member2);

            final var review1_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product1, 0L);
            final var review1_2 = 리뷰_이미지test5_평점5점_재구매O_생성(member2, product1, 0L);
            final var review2_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product2, 0L);
            복수_리뷰_저장(review1_1, review1_2, review2_1);

            복수_리뷰_태그_저장(리뷰태그_생성(review1_1, 태그_맛있어요), 리뷰태그_생성(review1_1, 태그_단짠단짠),
                    리뷰태그_생성(review1_1, 태그_갓성비), 리뷰태그_생성(review1_1, 태그_맛있어요), 리뷰태그_생성(review1_2, 태그_단짠단짠),
                    리뷰태그_생성(review2_1, 태그_맛있어요));

            final var expected = List.of(product2, product1);
            final var expected2 = List.of(product1);

            // when
            final var actual = productRepository.searchProductsByTopTagsFirst(태그1, PageRequest.of(0, 10));
            final var actual2 = productRepository.searchProductsByTopTagsFirst(태그2, PageRequest.of(0, 10));

            // then
            assertSoftly(soft -> {
                soft.assertThat(actual)
                        .isEqualTo(expected);
                soft.assertThat(actual2)
                        .isEqualTo(expected2);
            });
        }
    }

    @Nested
    class searchProductsByTopTagsFirst_실패_테스트 {

        @Test
        void 간식_태그는_4위이므로_검색되지_말아야한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var 태그_맛있어요 = 태그_맛있어요_TASTE_생성();
            final var 태그_단짠단짠 = 태그_단짠단짠_TASTE_생성();
            final var 태그_갓성비 = 태그_갓성비_PRICE_생성();
            final var 태그_간식 = 태그_간식_ETC_생성();

            final var 태그1 = 단일_태그_저장(태그_맛있어요);
            final var 태그2 = 단일_태그_저장(태그_단짠단짠);
            final var 태그3 = 단일_태그_저장(태그_갓성비);
            final var 태그4 = 단일_태그_저장(태그_간식);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            final var product3 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            final var product4 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2, product3, product4);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            복수_멤버_저장(member1, member2);

            final var review1_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product1, 0L);
            final var review1_2 = 리뷰_이미지test5_평점5점_재구매O_생성(member2, product1, 0L);
            final var review2_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product2, 0L);
            복수_리뷰_저장(review1_1, review1_2, review2_1);

            복수_리뷰_태그_저장(
                    리뷰태그_생성(review1_1, 태그_맛있어요),
                    리뷰태그_생성(review1_1, 태그_맛있어요),
                    리뷰태그_생성(review1_1, 태그_단짠단짠),
                    리뷰태그_생성(review1_1, 태그_단짠단짠),
                    리뷰태그_생성(review1_1, 태그_갓성비),
                    리뷰태그_생성(review1_1, 태그_갓성비),
                    리뷰태그_생성(review1_1, 태그_간식),
                    리뷰태그_생성(review1_1, 태그_맛있어요),
                    리뷰태그_생성(review1_2, 태그_단짠단짠),
                    리뷰태그_생성(review2_1, 태그_맛있어요)
            );

            // when
            final var actual = productRepository.searchProductsByTopTagsFirst(태그4, PageRequest.of(0, 10));

            // then
            assertThat(actual).isEmpty();
        }
    }

    @Nested
    class searchProductsByTopTags_성공_테스트 {

        @Test
        void 특정_태그와_마지막_상품아이디_이후_상품들을_조회한다() {
            // given
            final var category = 카테고리_간편식사_생성();
            단일_카테고리_저장(category);

            final var 태그_맛있어요 = 태그_맛있어요_TASTE_생성();
            final var 태그_단짠단짠 = 태그_단짠단짠_TASTE_생성();
            final var 태그_갓성비 = 태그_갓성비_PRICE_생성();

            final var 태그1 = 단일_태그_저장(태그_맛있어요);
            final var 태그2 = 단일_태그_저장(태그_단짠단짠);
            final var 태그3 = 단일_태그_저장(태그_갓성비);

            final var product1 = 상품_애플망고_가격3000원_평점5점_생성(category);
            final var product2 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            final var product3 = 상품_망고빙수_가격5000원_평점4점_생성(category);
            복수_상품_저장(product1, product2, product3);

            final var member1 = 멤버_멤버1_생성();
            final var member2 = 멤버_멤버2_생성();
            복수_멤버_저장(member1, member2);

            final var review1_1 = 리뷰_이미지test1_평점1점_재구매X_생성(member1, product1, 0L);
            final var review1_2 = 리뷰_이미지test5_평점5점_재구매O_생성(member2, product1, 0L);
            final var review2_1 = 리뷰_이미지test3_평점3점_재구매O_생성(member1, product2, 0L);
            복수_리뷰_저장(review1_1, review1_2, review2_1);

            복수_리뷰_태그_저장(리뷰태그_생성(review1_1, 태그_맛있어요), 리뷰태그_생성(review1_1, 태그_단짠단짠),
                    리뷰태그_생성(review1_1, 태그_갓성비), 리뷰태그_생성(review1_1, 태그_맛있어요), 리뷰태그_생성(review1_2, 태그_단짠단짠),
                    리뷰태그_생성(review2_1, 태그_맛있어요));

            final var expected = List.of(product1);

            // when
            final var actual = productRepository.searchProductsByTopTags(태그1, product2.getId(), PageRequest.of(0, 10));

            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }
    }
}
