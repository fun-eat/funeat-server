package com.funeat.acceptance.member;

import static io.restassured.RestAssured.given;

import com.funeat.member.dto.MemberRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import java.util.Objects;

@SuppressWarnings("NonAsciiCharacters")
public class MemberSteps {

    public static ExtractableResponse<Response> 사용자_정보_조회_요청(final String loginCookie) {
        return given()
                .cookie("SESSION", loginCookie)
                .when()
                .get("/api/members")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자_정보_수정_요청(final String loginCookie,
                                                             final MultiPartSpecification image,
                                                             final MemberRequest request) {
        final var requestSpec = given()
                .cookie("SESSION", loginCookie);

        if (Objects.nonNull(image)) {
            requestSpec.multiPart(image);
        }

        return requestSpec
                .multiPart("memberRequest", request, "application/json")
                .body(request)
                .when()
                .put("/api/members")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자_리뷰_조회_요청(final String loginCookie, final String sort,
                                                             final Long page) {
        return given()
                .when()
                .cookie("SESSION", loginCookie)
                .queryParam("sort", sort)
                .queryParam("page", page)
                .get("/api/members/reviews")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자_꿀조합_조회_요청(final String loginCookie, final String sort,
                                                              final Long page) {
        return given()
                .when()
                .cookie("SESSION", loginCookie)
                .queryParam("sort", sort)
                .queryParam("page", page)
                .get("/api/members/recipes")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 리뷰_삭제_요청(final String loginCookie, final Long reviewId) {
        return given()
                .cookie("SESSION", loginCookie)
                .when()
                .delete("/api/members/reviews/{reviewId}", reviewId)
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자_북마크한_꿀조합_조회_요청(final String loginCookie, final Long page) {
        return given()
                .when()
                .cookie("SESSION", loginCookie)
                .queryParam("page", page)
                .get("/api/members/recipes/bookmark")
                .then()
                .extract();
    }
}
