package com.funeat.product.presentation;

import com.funeat.product.domain.Product;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.domain.TagDto;
import java.util.ArrayList;
import java.util.List;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final Long price;
    private final String image;
    private final Double averageRating;
    private final List<TagDto> tags;

    public ProductResponse(final Long id, final String name, final Long price, final String image,
                           final Double averageRating, final List<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.averageRating = averageRating;
        this.tags = tags;
    }

    public static ProductResponse toResponse(final Product product, List<Tag> tags) {
        List<TagDto> tagDtos = new ArrayList<>();
        for (Tag tag : tags) {
            tagDtos.add(TagDto.toDto(tag));
        }
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImage(),
                product.getAverageRating(), tagDtos);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public List<TagDto> getTags() {
        return tags;
    }
}