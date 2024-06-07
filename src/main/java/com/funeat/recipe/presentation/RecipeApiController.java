package com.funeat.recipe.presentation;

import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.common.logging.Logging;
import com.funeat.recipe.application.RecipeService;
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
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RecipeApiController implements RecipeController {

    private final RecipeService recipeService;

    public RecipeApiController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Logging
    @PostMapping(value = "/api/recipes", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> writeRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                            @RequestPart(required = false) final List<MultipartFile> images,
                                            @RequestPart @Valid final RecipeCreateRequest recipeRequest) {
        final Long recipeId = recipeService.create(loginInfo.getId(), images, recipeRequest);

        return ResponseEntity.created(URI.create("/api/recipes/" + recipeId)).build();
    }

    @GetMapping(value = "/api/recipes/{recipeId}")
    public ResponseEntity<RecipeDetailResponse> getRecipeDetail(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                                @PathVariable final Long recipeId) {
        final RecipeDetailResponse response = recipeService.getRecipeDetail(loginInfo.getId(), recipeId);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/api/recipes")
    public ResponseEntity<SortingRecipesResponse> getSortingRecipes(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                                    @PageableDefault final Pageable pageable) {
        final SortingRecipesResponse response = recipeService.getSortingRecipes(loginInfo.getId(), pageable);

        return ResponseEntity.ok(response);
    }

    @Logging
    @PatchMapping(value = "/api/recipes/{recipeId}")
    public ResponseEntity<Void> likeRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                           @PathVariable final Long recipeId,
                                           @RequestBody @Valid final RecipeFavoriteRequest request) {
        recipeService.likeRecipe(loginInfo.getId(), recipeId, request);

        return ResponseEntity.noContent().build();
    }

    @Logging
    @PatchMapping(value = "/api/recipes/{recipeId}/bookmark")
    public ResponseEntity<Void> bookmarkRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                               @PathVariable final Long recipeId,
                                               @RequestBody @Valid final RecipeBookmarkRequest request) {
        recipeService.bookmarkRecipe(loginInfo.getId(), recipeId, request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/ranks/recipes")
    public ResponseEntity<RankingRecipesResponse> getRankingRecipes(@AuthenticationPrincipal final LoginInfo loginInfo) {
        final RankingRecipesResponse response = recipeService.getTop4Recipes(loginInfo.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/search/recipes/results")
    public ResponseEntity<SearchRecipeResultsResponse> getSearchResults(@RequestParam final String query,
                                                                        @RequestParam final Long lastRecipeId) {
        final SearchRecipeResultsResponse response = recipeService.getSearchResults(query, lastRecipeId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/recipes/{recipeId}/comments")
    public ResponseEntity<Void> writeComment(@AuthenticationPrincipal final LoginInfo loginInfo,
                                             @PathVariable final Long recipeId,
                                             @RequestBody @Valid final RecipeCommentCreateRequest request) {
        final Long savedCommentId = recipeService.writeCommentOfRecipe(loginInfo.getId(), recipeId, request);

        return ResponseEntity.created(URI.create("/api/recipes/" + recipeId + "/" + savedCommentId)).build();
    }

    @GetMapping("/api/recipes/{recipeId}/comments")
    public ResponseEntity<RecipeCommentsResponse> getCommentsOfRecipe(
            @AuthenticationPrincipal final LoginInfo loginInfo, @PathVariable final Long recipeId,
            @ModelAttribute final RecipeCommentCondition condition) {
        final RecipeCommentsResponse response = recipeService.getCommentsOfRecipe(recipeId, condition);

        return ResponseEntity.ok(response);
    }
}
