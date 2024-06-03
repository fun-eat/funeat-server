package com.funeat.product.persistence;

import com.funeat.product.domain.Product;
import com.funeat.tag.domain.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> searchProductsByTopTagsFirst(final Long tagId, final Pageable pageable) {
        final String jpql = """
                SELECT DISTINCT p\s
                FROM Product p
                WHERE p.id IN (
                    SELECT p2.id\s
                    FROM Product p2\s
                    JOIN p2.reviews r2\s
                    JOIN r2.reviewTags rt2\s
                    WHERE rt2.tag.id = :tagId\s
                      AND rt2.tag.id IN (
                          SELECT rt3.tag.id\s
                          FROM Review r3\s
                          JOIN r3.reviewTags rt3\s
                          WHERE r3.product.id = p2.id\s
                          GROUP BY rt3.tag.id\s
                          ORDER BY COUNT(rt3.tag.id) DESC\s
                          LIMIT 3
                      )
                )
                ORDER BY p.id DESC
                """;

        final TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("tagId", tagId);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

    @Override
    public List<Product> searchProductsByTopTags(final Long tagId, final Long lastProductId, final Pageable pageable) {
        final String jpql = """
                SELECT DISTINCT p\s
                FROM Product p\s
                WHERE p.id < :lastProductId\s
                AND p.id IN (
                    SELECT p2.id\s
                    FROM Product p2\s
                    JOIN p2.reviews r2\s
                    JOIN r2.reviewTags rt2\s
                    WHERE rt2.tag.id = :tagId\s
                    AND rt2.tag.id IN (
                        SELECT rt3.tag.id\s
                        FROM Review r3\s
                        JOIN r3.reviewTags rt3\s
                        WHERE r3.product.id = p2.id\s
                        GROUP BY rt3.tag.id\s
                        ORDER BY COUNT(rt3.tag.id) DESC\s
                        LIMIT 3
                    )
                    GROUP BY p2.id\s
                    HAVING COUNT(DISTINCT rt2.tag.id) <= 3\s
                )\s
                ORDER BY p.id DESC
                """;

        final TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("tagId", tagId);
        query.setParameter("lastProductId", lastProductId);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }
}
