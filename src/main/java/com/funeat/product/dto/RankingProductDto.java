package com.funeat.product.dto;

import com.funeat.product.domain.Product;

public class RankingProductDto {

    private final Long id;
    private final String name;
    private final Long price;
    private final String image;
    private final String categoryType;

    public RankingProductDto(final Long id, final String name, final Long price,
                             final String image, final String categoryType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.categoryType = categoryType;
    }

    public static RankingProductDto toDto(final Product product) {
        return new RankingProductDto(product.getId(), product.getName(), product.getPrice(),
                product.getImage(), product.getCategory().getType().getName());
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

    public String getCategoryType() {
        return categoryType;
    }
}
