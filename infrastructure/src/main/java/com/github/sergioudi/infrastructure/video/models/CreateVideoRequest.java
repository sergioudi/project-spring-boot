package com.github.sergioudi.infrastructure.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public record CreateVideoRequest(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("imdb") Double imdb,
        @JsonProperty("category_id") String categoryId,
        @JsonProperty("genres_id") List<String> genres,
        @JsonProperty("is_active") Boolean active
) {
    public boolean isActive() {
        return this.active != null ? this.active : true;
    }

    public List<String> genres() {
        return this.genres != null ? this.genres : Collections.emptyList();
    }
}
