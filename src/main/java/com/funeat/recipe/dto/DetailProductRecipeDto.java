package com.funeat.recipe.dto;

import com.funeat.product.domain.Product;

public class DetailProductRecipeDto {

    private final Long id;
    private final String name;
    private final Long price;

    private final String image;

    private DetailProductRecipeDto(final Long id, final String name, final Long price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static DetailProductRecipeDto toDto(final Product product) {
        return new DetailProductRecipeDto(product.getId(), product.getName(), product.getPrice(), product.getImage());
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
}
