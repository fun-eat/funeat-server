package com.funeat.recipe.presentation;

import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.recipe.dto.RankingRecipesResponse;
import com.funeat.recipe.dto.RecipeBookmarkRequest;
import com.funeat.recipe.dto.RecipeCommentCondition;
import com.funeat.recipe.dto.RecipeCommentCreateRequest;
import com.funeat.recipe.dto.RecipeCommentsResponse;
import com.funeat.recipe.dto.RecipeCreateRequest;
import com.funeat.recipe.dto.RecipeDetailResponse;
import com.funeat.recipe.dto.RecipeFavoriteRequest;
import com.funeat.recipe.dto.SearchRecipeResultsResponse;
import com.funeat.recipe.dto.SortingRecipesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "07.Recipe", description = "꿀조합 관련 API 입니다.")
public interface RecipeController {

    @Operation(summary = "꿀조합 추가", description = "꿀조합을 작성한다.")
    @ApiResponse(
            responseCode = "201",
            description = "꿀조합 작성 성공."
    )
    @PostMapping
    ResponseEntity<Void> writeRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                     @RequestPart final List<MultipartFile> images,
                                     @RequestPart final RecipeCreateRequest recipeRequest);

    @Operation(summary = "꿀조합 상세 조회", description = "꿀조합의 상세 정보를 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "꿀조합 상세 조회 성공."
    )
    @GetMapping
    ResponseEntity<RecipeDetailResponse> getRecipeDetail(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                         @PathVariable final Long recipeId);

    @Operation(summary = "꿀조합 목록 조회", description = "꿀조합의 목록을 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "꿀조합 목록 조회 성공."
    )
    @GetMapping
    ResponseEntity<SortingRecipesResponse> getSortingRecipes(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                             @PageableDefault final Pageable pageable);

    @Operation(summary = "꿀조합 좋아요", description = "꿀조합에 좋아요 또는 취소를 한다.")
    @ApiResponse(
            responseCode = "204",
            description = "꿀조합 좋아요(또는 좋아요 취소) 성공."
    )
    @PatchMapping
    ResponseEntity<Void> likeRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                    @PathVariable final Long recipeId,
                                    @RequestBody final RecipeFavoriteRequest request);

    @Operation(summary = "꿀조합 저장", description = "꿀조합을 저장 또는 취소를 한다.")
    @ApiResponse(
            responseCode = "204",
            description = "꿀조합 저장(또는 저장 취소) 성공."
    )
    @PatchMapping
    ResponseEntity<Void> bookmarkRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                        @PathVariable final Long recipeId,
                                        @RequestBody final RecipeBookmarkRequest request);

    @Operation(summary = "꿀조합 랭킹 조회", description = "전체 꿀조합들 중에서 랭킹 TOP4를 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "꿀조합 랭킹 조회 성공."
    )
    @GetMapping
    ResponseEntity<RankingRecipesResponse> getRankingRecipes(@AuthenticationPrincipal final LoginInfo loginInfo);

    @Operation(summary = "꿀조합 검색 결과 조회", description = "검색어가 포함된 상품이 있는 꿀조합 목록을 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "꿀조합 검색 결과 조회 성공."
    )
    @GetMapping
    ResponseEntity<SearchRecipeResultsResponse> getSearchResults(@RequestParam final String query,
                                                                 @RequestParam final Long lastRecipeId);

    @Operation(summary = "꿀조합 댓글 작성", description = "꿀조합 상세에서 댓글을 작성한다.")
    @ApiResponse(
            responseCode = "201",
            description = "꿀조합 댓글 작성 성공."
    )
    @PostMapping("/api/recipes/{recipeId}/comments")
    ResponseEntity<Void> writeComment(@AuthenticationPrincipal final LoginInfo loginInfo,
                                      @PathVariable final Long recipeId,
                                      @RequestBody final RecipeCommentCreateRequest request);

    @Operation(summary = "꿀조합 댓글 조회", description = "꿀조합 상세에서 댓글을 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "꿀조합 댓글 조회 성공."
    )
    @GetMapping("/api/recipes/{recipeId}/comments")
    ResponseEntity<RecipeCommentsResponse> getCommentsOfRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                               @PathVariable final Long recipeId,
                                                               @ModelAttribute final RecipeCommentCondition condition);
}
