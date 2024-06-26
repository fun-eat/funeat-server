package com.funeat.acceptance.review;

import static com.funeat.acceptance.auth.LoginSteps.로그인_쿠키_획득;
import static com.funeat.fixture.ReviewFixture.리뷰좋아요요청_생성;
import static io.restassured.RestAssured.given;

import com.funeat.review.dto.ReviewCreateRequest;
import com.funeat.review.dto.ReviewFavoriteRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("NonAsciiCharacters")
public class ReviewSteps {

    public static ExtractableResponse<Response> 리뷰_작성_요청(final String loginCookie, final Long productId,
                                                         final MultiPartSpecification image,
                                                         final ReviewCreateRequest request) {
        final var requestSpec = given()
                .cookie("SESSION", loginCookie);

        if (Objects.nonNull(image)) {
            requestSpec.multiPart(image);
        }

        return requestSpec
                .multiPart("reviewRequest", request, "application/json")
                .when()
                .post("/api/products/{productId}/reviews", productId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_좋아요_요청(final String loginCookie, final Long productId,
                                                          final Long reviewId, final ReviewFavoriteRequest request) {
        return given()
                .cookie("SESSION", loginCookie)
                .contentType("application/json")
                .body(request)
                .when()
                .patch("/api/products/{productId}/reviews/{reviewId}", productId, reviewId)
                .then()
                .extract();
    }

    public static void 여러명이_리뷰_좋아요_요청(final List<Long> memberIds, final Long productId, final Long reviewId,
                                      final Boolean favorite) {
        final var request = 리뷰좋아요요청_생성(favorite);

        for (final var memberId : memberIds) {
            리뷰_좋아요_요청(로그인_쿠키_획득(memberId), productId, reviewId, request);
        }
    }

    public static ExtractableResponse<Response> 정렬된_리뷰_목록_조회_요청(final Long productId, final Long lastReviewId,
                                                                    final String sort, final Long page) {
        return given()
                .queryParam("sort", sort)
                .queryParam("page", page)
                .queryParam("lastReviewId", lastReviewId)
                .when()
                .get("/api/products/{product_id}/reviews", productId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 정렬된_리뷰_목록_조회_요청(final String loginCookie, final Long productId,
                                                                final Long lastReviewId,
                                                                final String sort, final Long page) {
        return given()
                .cookie("SESSION", loginCookie)
                .queryParam("sort", sort)
                .queryParam("page", page)
                .queryParam("lastReviewId", lastReviewId)
                .when()
                .get("/api/products/{product_id}/reviews", productId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_랭킹_조회_요청() {
        return given()
                .when()
                .get("/api/ranks/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_랭킹_조회_요청(final String loginCookie) {
        return given()
                .cookie("SESSION", loginCookie)
                .when()
                .get("/api/ranks/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 좋아요를_제일_많이_받은_리뷰_조회_요청(final Long productId) {
        return given()
                .when()
                .get("/api/ranks/products/{product_id}/reviews", productId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_상세_조회_요청(final Long reviewId) {
        return given()
                .when()
                .get("/api/reviews/{reviewId}", reviewId)
                .then()
                .extract();
    }
}
