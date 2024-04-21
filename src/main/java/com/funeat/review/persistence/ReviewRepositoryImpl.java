package com.funeat.review.persistence;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.review.domain.Review;
import com.funeat.review.dto.SortingReviewDtoWithoutTag;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CompoundSelection;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl implements ReviewCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SortingReviewDtoWithoutTag> getSortingReview(final Member loginMember,
                                                             final Specification<Review> specification,
                                                             final String sortOption) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<SortingReviewDtoWithoutTag> cq = cb.createQuery(SortingReviewDtoWithoutTag.class);
        final Root<Review> root = cq.from(Review.class);

        // sortField, sortOrder
        final String[] sortOptionSplit = sortOption.split(",");
        final String sortField = sortOptionSplit[0];
        final String sortOrder = sortOptionSplit[1];

        // join
        final Join<Review, Member> joinMember = root.join("member", JoinType.INNER);

        // left join
        final Join<Review, ReviewFavorite> leftJoinReviewFavorite = root.join("reviewFavorites", JoinType.LEFT);
        leftJoinReviewFavorite.on(cb.disjunction()); // 비로그인 좋아요 기본값은 false
        if (loginMember.isMember()) {
            final Predicate condition = cb.equal(leftJoinReviewFavorite.get("member"), loginMember);
            leftJoinReviewFavorite.on(condition);
        }

        // select - from - where - order by
        cq.select(getConstruct(root, cb, joinMember, leftJoinReviewFavorite))
                .where(specification.toPredicate(root, cq, cb))
                .orderBy(getOrderBy(root, cb, sortField, sortOrder));

        // limit
        final TypedQuery<SortingReviewDtoWithoutTag> query = em.createQuery(cq);
        query.setMaxResults(11);

        // result
        return query.getResultList();
    }

    private CompoundSelection<SortingReviewDtoWithoutTag> getConstruct(final Root<Review> root,
                                                                       final CriteriaBuilder cb,
                                                                       final Join<Review, Member> joinMember,
                                                                       final Join<Review, ReviewFavorite> leftJoinReviewFavorite) {

        return cb.construct(SortingReviewDtoWithoutTag.class,
                root.get("id"),
                joinMember.get("nickname"),
                joinMember.get("profileImage"),
                root.get("image"),
                root.get("rating"),
                root.get("content"),
                root.get("reBuy"),
                root.get("favoriteCount"),
                leftJoinReviewFavorite.get("favorite"),
                root.get("createdAt"));
    }

    private List<Order> getOrderBy(final Root<Review> root,
                                   final CriteriaBuilder cb,
                                   final String fieldName,
                                   final String sortOption) {
        if ("ASC".equalsIgnoreCase(sortOption)) {
            final Order order = cb.asc(root.get(fieldName));
            return List.of(order, cb.desc(root.get("id")));
        }
        final Order order = cb.desc(root.get(fieldName));
        return List.of(order, cb.desc(root.get("id")));
    }
}
