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
                SELECT DISTINCT p
                FROM Product p
                JOIN p.reviews r
                JOIN r.reviewTags rt
                JOIN (
                    SELECT rt2.tag.id AS tagId
                    FROM ReviewTag rt2
                    GROUP BY rt2.tag.id
                    ORDER BY COUNT(rt2.tag.id) DESC
                    LIMIT 3
                ) AS topTags
                ON rt.tag.id = topTags.tagId
                WHERE rt.tag.id = :tagId
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
                SELECT DISTINCT p
                  FROM Product p
                  JOIN p.reviews r
                  JOIN r.reviewTags rt
                  JOIN (
                      SELECT rt2.tag.id AS tagId
                      FROM ReviewTag rt2
                      GROUP BY rt2.tag.id
                      ORDER BY COUNT(rt2.tag.id) DESC
                      LIMIT 3
                  ) AS topTags
                  ON rt.tag.id = topTags.tagId
                  WHERE rt.tag.id = :tagId
                  AND p.id < :lastProductId
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
