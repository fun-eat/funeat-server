package com.funeat.product.domain;

import com.funeat.review.domain.Review;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {

    public static final String BASIC_IMAGE = null;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    private String image;

    private String content;

    private Double averageRating = 0.0;

    private Long reviewCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    private List<ProductRecipe> productRecipes;

    protected Product() {
    }

    public Product(final String name, final Long price, final String image, final String content,
                   final Category category) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.content = content;
        this.category = category;
    }

    public Product(final String name, final Long price, final String image, final String content,
                   final Double averageRating, final Category category) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.content = content;
        this.averageRating = averageRating;
        this.category = category;
    }

    public Product(final String name, final Long price, final String image, final String content,
                   final Category category, final Long reviewCount) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.content = content;
        this.category = category;
        this.reviewCount = reviewCount;
    }

    public Product(final String name, final Long price, final String image, final String content,
                   final Double averageRating, final Category category, final Long reviewCount) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.content = content;
        this.averageRating = averageRating;
        this.category = category;
        this.reviewCount = reviewCount;
    }

    public static Product create(final String name, final Long price, final String content, final Category category) {
        return new Product(name, price, null, content, category);
    }

    public void updateAverageRatingForInsert(final Long count, final Long rating) {
        final double calculatedRating = ((count - 1) * averageRating + rating) / count;
        this.averageRating = Math.round(calculatedRating * 10.0) / 10.0;
    }

    public void updateAverageRatingForDelete(final Long deletedRating) {
        if (reviewCount == 1) {
            this.averageRating = 0.0;
            return;
        }
        final double calculatedRating = (reviewCount * averageRating - deletedRating) / (reviewCount - 1);
        this.averageRating = Math.round(calculatedRating * 10.0) / 10.0;
    }

    public Double calculateRankingScore(final Long reviewCount) {
        final double exponent = -Math.log10(reviewCount + 1);
        final double factor = Math.pow(2, exponent);
        return averageRating - (averageRating - 3.0) * factor;
    }

    public void updateBasicImage() {
        this.image = BASIC_IMAGE;
    }

    public void updateFavoriteImage(final String topFavoriteImage) {
        this.image = topFavoriteImage;
    }

    public void update(final String name, final String content, final Long price, final Category category) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Category getCategory() {
        return category;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public void addReviewCount() {
        reviewCount++;
    }

    public void minusReviewCount() {
        reviewCount--;
    }
}
