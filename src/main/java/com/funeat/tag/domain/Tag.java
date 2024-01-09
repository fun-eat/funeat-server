package com.funeat.tag.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TagType tagType;

    protected Tag() {
    }

    public Tag(final String name, final TagType tagType) {
        this.name = name;
        this.tagType = tagType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TagType getTagType() {
        return tagType;
    }
}
