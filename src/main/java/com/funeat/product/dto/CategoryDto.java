package com.funeat.product.dto;

import com.funeat.product.domain.Category;

public class CategoryDto {

    private final Long id;
    private final String name;
    private final String image;

    private CategoryDto(final Long id, final String name, final String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public static CategoryDto toDto(final Category category) {
        return new CategoryDto(category.getId(), category.getName(), category.getImage());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
