package com.github.sergioudi.infrastructure.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record VideoListResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("imdb") Double imdb,
        @JsonProperty("is_active") Boolean active,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("deleted_at") Instant deletedAt
) {
}
