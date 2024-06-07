package com.funeat.product.persistence;

import com.funeat.product.domain.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> searchProductsByTopTagsFirst(final Long tagId, final Pageable pageable);

    List<Product> searchProductsByTopTags(final Long tagId, final Long lastProductId, final Pageable pageable);
}
