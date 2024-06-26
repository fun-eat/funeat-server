package com.funeat.review.persistence;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

import com.funeat.member.domain.Member;
import com.funeat.product.domain.Product;
import com.funeat.review.domain.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

    Long countByProduct(final Product product);

    Long countByMember(final Member member);

    Page<Review> findReviewsByMember(final Member findMember, final Pageable pageable);

    @Lock(PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Review r WHERE r.id=:id")
    Optional<Review> findByIdForUpdate(final Long id);

    @Query("SELECT r "
            + "FROM Review r "
            + "LEFT JOIN r.product p "
            + "WHERE p.id = :id AND r.image != '' "
            + "ORDER BY r.favoriteCount DESC, r.id DESC")
    List<Review> findPopularReviewWithImage(@Param("id") final Long productId, final Pageable pageable);

    Optional<Review> findTopByProductOrderByFavoriteCountDescIdDesc(final Product product);

    List<Review> findReviewsByFavoriteCountGreaterThanEqual(final Long favoriteCount);
}
