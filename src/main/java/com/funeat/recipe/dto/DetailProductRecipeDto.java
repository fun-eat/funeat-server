package com.funeat.recipe.dto;

import com.funeat.product.domain.Product;

public class DetailProductRecipeDto {

    private final Long id;
    private final String name;
    private final Long price;
    private final String image;
    private final Double averageRating;

    private DetailProductRecipeDto(final Long id, final String name, final Long price, final String image,
                                   final Double averageRating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.averageRating = averageRating;
    }

    public static DetailProductRecipeDto toDto(final Product product) {
        return new DetailProductRecipeDto(product.getId(), product.getName(), product.getPrice(), product.getImage(),
                product.getAverageRating());
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

    public Double getAverageRating() {
        return averageRating;
    }
}
