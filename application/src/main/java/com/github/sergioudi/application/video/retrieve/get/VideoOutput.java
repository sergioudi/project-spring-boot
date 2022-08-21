package com.github.sergioudi.application.video.retrieve.get;

import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreID;
import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoID;

import java.time.Instant;
import java.util.List;

public record VideoOutput(
        VideoID id,
        String name,
        String description,
        Double imdb,
        CategoryID categoryID,
        List<String> genres,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static VideoOutput from(final Video aVideo) {
        return new VideoOutput(
                aVideo.getId(),
                aVideo.getName(),
                aVideo.getDescription(),
                aVideo.getImdb(),
                aVideo.getCategoryID(),
                aVideo.getGenres().stream()
                        .map(GenreID::getValue)
                        .toList(),
                aVideo.isActive(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getDeletedAt()
        );
    }
}
