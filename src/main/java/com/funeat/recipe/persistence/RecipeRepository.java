package com.funeat.recipe.persistence;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.recipe.domain.Recipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r "
            + "JOIN FETCH r.member m")
    Recipe findRecipeWithMemberById(final Long id);

    Page<Recipe> findRecipesByMember(final Member member, final Pageable pageable);

    @Query("SELECT DISTINCT r FROM Recipe r "
            + "LEFT JOIN ProductRecipe pr ON pr.recipe.id = r.id "
            + "WHERE pr.product.name LIKE CONCAT('%', :name, '%')"
            + "ORDER BY r.id DESC")
    List<Recipe> findAllByProductNameContainingFirst(@Param("name") final String name, final Pageable pageable);

    @Query("SELECT DISTINCT r FROM Recipe r "
            + "LEFT JOIN ProductRecipe pr ON pr.recipe.id = r.id "
            + "JOIN Recipe last ON last.id = :lastId "
            + "LEFT JOIN ProductRecipe last_pr ON last_pr.recipe.id = last.id "
            + "WHERE pr.product.name LIKE CONCAT('%', :name, '%') AND r.id < :lastId "
            + "ORDER BY r.id DESC")
    List<Recipe> findAllByProductNameContaining(@Param("name") final String name, final Long lastId,
                                                final Pageable pageable);

    Page<Recipe> findAll(final Pageable pageable);

    @Query("SELECT r FROM Recipe r LEFT JOIN ProductRecipe pr ON pr.product = :product WHERE pr.recipe.id = r.id")
    Page<Recipe> findRecipesByProduct(final Product product, final Pageable pageable);

    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Recipe r WHERE r.id=:id")
    Optional<Recipe> findByIdForUpdate(final Long id);

    List<Recipe> findRecipesByFavoriteCountGreaterThanEqual(final Long favoriteCount);

    Long countByMember(final Member member);
}
