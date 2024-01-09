package com.funeat.member.domain.favorite;

import com.funeat.member.domain.Member;
import com.funeat.recipe.domain.Recipe;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "recipe_id"}))
public class RecipeFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private Boolean favorite;

    protected RecipeFavorite() {
    }

    public RecipeFavorite(final Member member, final Recipe recipe, final Boolean favorite) {
        this.member = member;
        this.recipe = recipe;
        this.favorite = favorite;
    }

    public static RecipeFavorite create(final Member member, final Recipe recipe, final Boolean favorite) {
        if (favorite == true) {
            recipe.addFavoriteCount();
        }
        return new RecipeFavorite(member, recipe, favorite);
    }

    public void updateFavorite(final Boolean favorite) {
        if (!this.favorite && favorite) {
            this.recipe.addFavoriteCount();
            this.favorite = favorite;
            return;
        }
        if (this.favorite && !favorite) {
            this.recipe.minusFavoriteCount();
            this.favorite = favorite;
        }
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Boolean getFavorite() {
        return favorite;
    }
}
