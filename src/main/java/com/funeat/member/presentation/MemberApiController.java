package com.funeat.member.presentation;

import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.common.logging.Logging;
import com.funeat.member.application.MemberService;
import com.funeat.member.dto.MemberBookmarkRecipesResponse;
import com.funeat.member.dto.MemberProfileResponse;
import com.funeat.member.dto.MemberRecipesResponse;
import com.funeat.member.dto.MemberRequest;
import com.funeat.member.dto.MemberReviewsResponse;
import com.funeat.recipe.application.RecipeService;
import com.funeat.review.application.ReviewService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/members")
public class MemberApiController implements MemberController {

    private final MemberService memberService;
    private final ReviewService reviewService;
    private final RecipeService recipeService;

    public MemberApiController(final MemberService memberService, final ReviewService reviewService,
                               final RecipeService recipeService) {
        this.memberService = memberService;
        this.reviewService = reviewService;
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<MemberProfileResponse> getMemberProfile(@AuthenticationPrincipal final LoginInfo loginInfo) {
        final MemberProfileResponse response = memberService.getMemberProfile(loginInfo.getId());

        return ResponseEntity.ok(response);
    }

    @Logging
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> putMemberProfile(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                 @RequestPart(required = false) final MultipartFile image,
                                                 @RequestPart @Valid final MemberRequest memberRequest) {
        memberService.modify(loginInfo.getId(), image, memberRequest);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reviews")
    public ResponseEntity<MemberReviewsResponse> getMemberReview(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                                 @PageableDefault final Pageable pageable) {
        final MemberReviewsResponse response = reviewService.findReviewByMember(loginInfo.getId(), pageable);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/recipes")
    public ResponseEntity<MemberRecipesResponse> getMemberRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                                 @PageableDefault final Pageable pageable) {
        final MemberRecipesResponse response = recipeService.findRecipeByMember(loginInfo.getId(), pageable);

        return ResponseEntity.ok().body(response);
    }

    @Logging
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable final Long reviewId,
                                             @AuthenticationPrincipal final LoginInfo loginInfo) {
        reviewService.deleteReview(reviewId, loginInfo.getId());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recipes/bookmark")
    public ResponseEntity<MemberBookmarkRecipesResponse> getMemberBookmarkRecipe(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                                                 @PageableDefault final Pageable pageable) {
        final MemberBookmarkRecipesResponse response = recipeService.findBookmarkRecipeByMember(loginInfo.getId(), pageable);

        return ResponseEntity.ok().body(response);
    }
}
