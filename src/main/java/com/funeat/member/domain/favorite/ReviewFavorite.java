package com.funeat.member.domain.favorite;

import com.funeat.member.domain.Member;
import com.funeat.review.domain.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "review_id"}))
public class ReviewFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private Boolean favorite;

    protected ReviewFavorite() {
    }

    public ReviewFavorite(final Member member, final Review review) {
        this.member = member;
        this.review = review;
    }

    public static ReviewFavorite create(final Member member, final Review review, final Boolean favorite) {
        final ReviewFavorite reviewFavorite = new ReviewFavorite(member, review);
        reviewFavorite.review.getReviewFavorites().add(reviewFavorite);
        reviewFavorite.member.getReviewFavorites().add(reviewFavorite);
        reviewFavorite.favorite = favorite;
        reviewFavorite.review.addFavoriteCount();
        return reviewFavorite;
    }

    public void updateChecked(final Boolean favorite) {
        if (!this.favorite && favorite) {
            this.review.addFavoriteCount();
            this.favorite = favorite;
            return;
        }
        if (this.favorite && !favorite) {
            this.review.minusFavoriteCount();
            this.favorite = favorite;
        }
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Review getReview() {
        return review;
    }

    public Boolean getFavorite() {
        return favorite;
    }
}
