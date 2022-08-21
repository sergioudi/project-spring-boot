package com.github.sergioudi.infrastructure.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.sergioudi.domain.category.Category;

import java.time.Instant;
import java.util.List;

public record VideoResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("imdb") Double imdb,
        @JsonProperty("category_id") String categoryId,
        @JsonProperty("genres_id") List<String> genres,
        @JsonProperty("is_active") Boolean active,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt,
        @JsonProperty("deleted_at") Instant deletedAt
) {
}
