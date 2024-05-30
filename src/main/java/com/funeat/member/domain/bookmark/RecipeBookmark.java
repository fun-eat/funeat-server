package com.funeat.member.domain.bookmark;

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
public class RecipeBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private Boolean bookmark;

    protected RecipeBookmark() {
    }

    public RecipeBookmark(final Member member, final Recipe recipe, final Boolean bookmark) {
        this.member = member;
        this.recipe = recipe;
        this.bookmark = bookmark;
    }

    public static RecipeBookmark create(final Member member, final Recipe recipe, final Boolean bookmark) {
        return new RecipeBookmark(member, recipe, bookmark);
    }

    public void updateBookmark(final Boolean bookmark) {
        this.bookmark = bookmark;
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

    public Boolean getBookmark() {
        return bookmark;
    }
}
