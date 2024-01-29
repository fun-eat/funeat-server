package com.funeat.review.domain;

import com.funeat.tag.domain.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ReviewTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    protected ReviewTag() {
    }

    private ReviewTag(final Review review, final Tag tag) {
        this.review = review;
        this.tag = tag;
    }

    public static ReviewTag createReviewTag(final Review review, final Tag tag) {
        final ReviewTag reviewTag = new ReviewTag(review, tag);
        review.getReviewTags().add(reviewTag);
        return reviewTag;
    }

    public Long getId() {
        return id;
    }

    public Review getReview() {
        return review;
    }

    public Tag getTag() {
        return tag;
    }
}
