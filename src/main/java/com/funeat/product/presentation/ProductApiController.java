package com.funeat.product.presentation;

import com.funeat.auth.dto.LoginInfo;
import com.funeat.auth.util.AuthenticationPrincipal;
import com.funeat.product.application.ProductService;
import com.funeat.product.dto.ProductResponse;
import com.funeat.product.dto.ProductSortCondition;
import com.funeat.product.dto.ProductsInCategoryResponse;
import com.funeat.product.dto.RankingProductsResponse;
import com.funeat.product.dto.SearchProductResultsResponse;
import com.funeat.product.dto.SearchProductsResponse;
import com.funeat.recipe.dto.SortingRecipesResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductApiController implements ProductController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<ProductsInCategoryResponse> getAllProductsInCategory(@PathVariable final Long categoryId,
                                                                               @RequestParam final Long lastProductId,
                                                                               @RequestParam final String sort) {
        final ProductSortCondition sortCondition = ProductSortCondition.toDto(sort);
        final ProductsInCategoryResponse response = productService.getAllProductsInCategory(categoryId, lastProductId, sortCondition);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> getProductDetail(@PathVariable final Long productId) {
        final ProductResponse response = productService.findProductDetail(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ranks/products")
    public ResponseEntity<RankingProductsResponse> getRankingProducts() {
        final RankingProductsResponse response = productService.getTop3Products();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/products")
    public ResponseEntity<SearchProductsResponse> searchProducts(@RequestParam final String query,
                                                                 @RequestParam final Long lastProductId) {
        final SearchProductsResponse response = productService.searchProducts(query, lastProductId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/products/results")
    public ResponseEntity<SearchProductResultsResponse> getSearchResults(@RequestParam final String query,
                                                                         @RequestParam final Long lastProductId) {
        final SearchProductResultsResponse response = productService.getSearchResults(query, lastProductId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{productId}/recipes")
    public ResponseEntity<SortingRecipesResponse> getProductRecipes(@AuthenticationPrincipal final LoginInfo loginInfo,
                                                                    @PathVariable final Long productId,
                                                                    @PageableDefault final Pageable pageable) {
        final SortingRecipesResponse response = productService.getProductRecipes(loginInfo.getId(), productId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/tags/results")
    public ResponseEntity<SearchProductsResponse> getSearchResultByTag(@RequestParam final Long tagId,
                                                                       @RequestParam final Long lastProductId) {
        final SearchProductsResponse response = productService.getSearchResultsByTag(tagId, lastProductId);
        return ResponseEntity.ok(response);
    }
}
