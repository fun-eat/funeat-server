package com.funeat.review.application;

import static com.funeat.member.exception.MemberErrorCode.MEMBER_DUPLICATE_FAVORITE;
import static com.funeat.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static com.funeat.review.exception.ReviewErrorCode.NOT_AUTHOR_OF_REVIEW;
import static com.funeat.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;

import com.funeat.common.ImageUploader;
import com.funeat.common.dto.PageDto;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.member.dto.MemberReviewDto;
import com.funeat.member.dto.MemberReviewsResponse;
import com.funeat.member.exception.MemberException.MemberDuplicateFavoriteException;
import com.funeat.member.exception.MemberException.MemberNotFoundException;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.ReviewFavoriteRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.exception.ProductException.ProductNotFoundException;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.review.dto.MostFavoriteReviewResponse;
import com.funeat.review.dto.RankingReviewDto;
import com.funeat.review.dto.RankingReviewsResponse;
import com.funeat.review.dto.ReviewCreateRequest;
import com.funeat.review.dto.ReviewDetailResponse;
import com.funeat.review.dto.ReviewFavoriteRequest;
import com.funeat.review.dto.SortingReviewDto;
import com.funeat.review.dto.SortingReviewDtoWithoutTag;
import com.funeat.review.dto.SortingReviewRequest;
import com.funeat.review.dto.SortingReviewsResponse;
import com.funeat.review.exception.ReviewException.NotAuthorOfReviewException;
import com.funeat.review.exception.ReviewException.ReviewNotFoundException;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.review.specification.SortingReviewSpecification;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private static final int FIRST_PAGE = 0;
    private static final int START_INDEX = 0;
    private static final int ONE = 1;
    private static final String EMPTY_URL = "";
    private static final int RANKING_SIZE = 2;
    private static final long RANKING_MINIMUM_FAVORITE_COUNT = 1L;
    private static final int REVIEW_PAGE_SIZE = 10;
    private static final long GUEST_ID = -1L;

    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewFavoriteRepository reviewFavoriteRepository;
    private final ImageUploader imageUploader;
    private final ApplicationEventPublisher eventPublisher;

    public ReviewService(final ReviewRepository reviewRepository, final TagRepository tagRepository,
                         final ReviewTagRepository reviewTagRepository, final MemberRepository memberRepository,
                         final ProductRepository productRepository,
                         final ReviewFavoriteRepository reviewFavoriteRepository,
                         final ImageUploader imageUploader, final ApplicationEventPublisher eventPublisher) {
        this.reviewRepository = reviewRepository;
        this.tagRepository = tagRepository;
        this.reviewTagRepository = reviewTagRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.reviewFavoriteRepository = reviewFavoriteRepository;
        this.imageUploader = imageUploader;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void create(final Long productId, final Long memberId, final MultipartFile image,
                       final ReviewCreateRequest reviewRequest) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final String imageUrl = Optional.ofNullable(image)
                .map(imageUploader::upload)
                .orElse(EMPTY_URL);
        final Review savedReview = reviewRepository.save(
                new Review(findMember, findProduct, imageUrl, reviewRequest.getRating(), reviewRequest.getContent(),
                        reviewRequest.getRebuy()));

        final List<Tag> findTags = tagRepository.findTagsByIdIn(reviewRequest.getTagIds());

        final List<ReviewTag> reviewTags = findTags.stream()
                .map(findTag -> ReviewTag.createReviewTag(savedReview, findTag))
                .collect(Collectors.toList());

        final Long countByProduct = reviewRepository.countByProduct(findProduct);

        findProduct.updateAverageRatingForInsert(countByProduct, savedReview.getRating());
        findProduct.addReviewCount();
        reviewTagRepository.saveAll(reviewTags);
    }

    @Transactional
    public void likeReview(final Long reviewId, final Long memberId, final ReviewFavoriteRequest request) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        final Review findReview = reviewRepository.findByIdForUpdate(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND, reviewId));

        final ReviewFavorite savedReviewFavorite = reviewFavoriteRepository.findByMemberAndReview(findMember,
                findReview).orElseGet(() -> saveReviewFavorite(findMember, findReview, request.getFavorite()));

        savedReviewFavorite.updateChecked(request.getFavorite());
    }

    private ReviewFavorite saveReviewFavorite(final Member member, final Review review, final Boolean favorite) {
        try {
            final ReviewFavorite reviewFavorite = ReviewFavorite.create(member, review, favorite);
            return reviewFavoriteRepository.save(reviewFavorite);
        } catch (final DataIntegrityViolationException e) {
            throw new MemberDuplicateFavoriteException(MEMBER_DUPLICATE_FAVORITE, member.getId());
        }
    }

    @Transactional
    public void updateProductImage(final Long productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final PageRequest pageRequest = PageRequest.of(FIRST_PAGE, ONE);

        final List<Review> topFavoriteReview = reviewRepository.findPopularReviewWithImage(productId, pageRequest);
        if (topFavoriteReview.isEmpty()) {
            product.updateBasicImage();
            return;
        }
        final String topFavoriteReviewImage = topFavoriteReview.get(START_INDEX).getImage();
        product.updateFavoriteImage(topFavoriteReviewImage);
    }

    public SortingReviewsResponse sortingReviews(final Long productId, final Long memberId,
                                                 final SortingReviewRequest request) {
        final Member guestOrFindMember = memberRepository.findById(memberId)
                .orElse(Member.createGuest());
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final List<SortingReviewDto> sortingReviews = getSortingReviews(guestOrFindMember, findProduct, request);
        final int resultSize = getResultSize(sortingReviews);

        final List<SortingReviewDto> resizeSortingReviews = sortingReviews.subList(START_INDEX, resultSize);
        final Boolean hasNext = hasNextPage(sortingReviews);

        return SortingReviewsResponse.toResponse(resizeSortingReviews, hasNext);
    }

    private List<SortingReviewDto> getSortingReviews(final Member guestOrFindMember, final Product product,
                                                     final SortingReviewRequest request) {
        final Long lastReviewId = request.getLastReviewId();
        final String sortOption = request.getSort();

        final Specification<Review> specification = getSortingSpecification(product, sortOption, lastReviewId);
        final List<SortingReviewDtoWithoutTag> sortingReviewDtoWithoutTags = reviewRepository.getSortingReview(guestOrFindMember, specification, sortOption);

        return addTagsToSortingReviews(sortingReviewDtoWithoutTags);
    }

    private List<SortingReviewDto> addTagsToSortingReviews(
            final List<SortingReviewDtoWithoutTag> sortingReviewDtoWithoutTags) {
        return sortingReviewDtoWithoutTags.stream()
                .map(reviewDto -> SortingReviewDto.toDto(reviewDto,
                        tagRepository.findTagsByReviewId(reviewDto.getId())))
                .collect(Collectors.toList());
    }

    private Specification<Review> getSortingSpecification(final Product product, final String sortOption,
                                                          final Long lastReviewId) {
        if (lastReviewId == FIRST_PAGE) {
            return SortingReviewSpecification.sortingFirstPageBy(product);
        }

        final Review lastReview = reviewRepository.findById(lastReviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND, lastReviewId));

        return SortingReviewSpecification.sortingBy(product, sortOption, lastReview);
    }

    private int getResultSize(final List<SortingReviewDto> sortingReviews) {
        if (sortingReviews.size() <= REVIEW_PAGE_SIZE) {
            return sortingReviews.size();
        }
        return REVIEW_PAGE_SIZE;
    }

    private Boolean hasNextPage(final List<SortingReviewDto> sortingReviews) {
        return sortingReviews.size() > REVIEW_PAGE_SIZE;
    }

    public RankingReviewsResponse getTopReviews(final Long memberId) {
        final List<Review> reviews = reviewRepository.findReviewsByFavoriteCountGreaterThanEqual(RANKING_MINIMUM_FAVORITE_COUNT);
        final List<RankingReviewDto> dtos = reviews.stream()
                .sorted(Comparator.comparing(Review::calculateRankingScore).reversed())
                .limit(RANKING_SIZE)
                .map(review -> createRankingReviewDto(memberId, review))
                .toList();
        return RankingReviewsResponse.toResponse(dtos);
    }

    private RankingReviewDto createRankingReviewDto(final Long memberId, final Review review) {
        if (memberId == GUEST_ID) {
            return RankingReviewDto.toDto(review, false);
        }

        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        final Boolean favorite = reviewFavoriteRepository.existsByMemberAndReviewAndFavoriteTrue(member, review);
        return RankingReviewDto.toDto(review, favorite);
    }

    public MemberReviewsResponse findReviewByMember(final Long memberId, final Pageable pageable) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));

        final Page<Review> sortedReviewPages = reviewRepository.findReviewsByMember(findMember, pageable);
        final PageDto pageDto = PageDto.toDto(sortedReviewPages);

        final List<MemberReviewDto> dtos = sortedReviewPages.stream()
                .map(this::transformMemberReviewDtoWithReviewAndTag)
                .toList();

        return MemberReviewsResponse.toResponse(pageDto, dtos);
    }

    private MemberReviewDto transformMemberReviewDtoWithReviewAndTag(final Review review) {
        final List<Tag> tags = tagRepository.findTagsByReviewId(review.getId());

        return MemberReviewDto.toDto(review, tags);
    }

    @Transactional
    public void deleteReview(final Long reviewId, final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND, reviewId));
        final Product product = review.getProduct();
        final String image = review.getImage();

        if (review.checkAuthor(member)) {
            eventPublisher.publishEvent(new ReviewDeleteEvent(image));
            deleteThingsRelatedToReview(review);
            updateProduct(product, review.getRating());
            return;
        }
        throw new NotAuthorOfReviewException(NOT_AUTHOR_OF_REVIEW, memberId);
    }

    private void deleteThingsRelatedToReview(final Review review) {
        deleteReviewTags(review);
        deleteReviewFavorites(review);
        reviewRepository.delete(review);
    }

    private void deleteReviewTags(final Review review) {
        final List<ReviewTag> reviewTags = reviewTagRepository.findByReview(review);
        final List<Long> ids = reviewTags.stream()
                .map(ReviewTag::getId)
                .collect(Collectors.toList());
        reviewTagRepository.deleteAllByIdInBatch(ids);
    }

    private void deleteReviewFavorites(final Review review) {
        final List<ReviewFavorite> reviewFavorites = reviewFavoriteRepository.findByReview(review);
        final List<Long> ids = reviewFavorites.stream()
                .map(ReviewFavorite::getId)
                .collect(Collectors.toList());
        reviewFavoriteRepository.deleteAllByIdInBatch(ids);
    }

    private void updateProduct(final Product product, final Long deletedRating) {
        product.updateAverageRatingForDelete(deletedRating);
        product.minusReviewCount();
        updateProductImage(product.getId());
    }

    public Optional<MostFavoriteReviewResponse> getMostFavoriteReview(final Long productId) {
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final Optional<Review> review = reviewRepository.findTopByProductOrderByFavoriteCountDescIdDesc(findProduct);

        return MostFavoriteReviewResponse.toResponse(review);
    }

    public ReviewDetailResponse getReviewDetail(final Long reviewId) {
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND, reviewId));
        return ReviewDetailResponse.toResponse(review);
    }
}
