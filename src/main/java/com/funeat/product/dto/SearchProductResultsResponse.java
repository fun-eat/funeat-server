package com.funeat.product.dto;

import java.util.List;

public class SearchProductResultsResponse {

    private final boolean hasNext;
    private final List<SearchProductResultDto> products;

    private SearchProductResultsResponse(final boolean hasNext, final List<SearchProductResultDto> products) {
        this.hasNext = hasNext;
        this.products = products;
    }

    public static SearchProductResultsResponse toResponse(final boolean hasNext,
                                                          final List<SearchProductResultDto> products) {
        return new SearchProductResultsResponse(hasNext, products);
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public List<SearchProductResultDto> getProducts() {
        return products;
    }
}
