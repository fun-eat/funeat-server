package com.funeat.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    private String image;

    protected Category() {
    }

    public Category(final String name, final CategoryType type, final String image) {
        this.name = name;
        this.type = type;
        this.image = image;
    }

    public boolean isFood() {
        return type == CategoryType.FOOD;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }

    public String getImage() {
        return image;
    }
}
