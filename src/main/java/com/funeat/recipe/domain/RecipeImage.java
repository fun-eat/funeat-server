package com.funeat.recipe.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RecipeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    protected RecipeImage() {
    }

    public RecipeImage(final String image, final Recipe recipe) {
        this.image = image;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
