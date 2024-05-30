package com.funeat.member.persistence;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.bookmark.RecipeBookmark;
import com.funeat.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeBookmarkRepository extends JpaRepository<RecipeBookmark, Long> {

    Optional<RecipeBookmark> findByMemberAndRecipe(final Member member, final Recipe recipe);
}
