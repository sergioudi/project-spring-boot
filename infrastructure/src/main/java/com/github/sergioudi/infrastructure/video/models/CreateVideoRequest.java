package com.github.sergioudi.infrastructure.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateVideoRequest(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("imdb") Double imdb,
        @JsonProperty("category_id") String categoryId,
        @JsonProperty("genre_id") String genreId,
        @JsonProperty("is_active") Boolean active
) {
}
