package com.github.sergioudi.application.video.retrieve.get;

import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoID;

import java.time.Instant;

public record VideoOutput(
        VideoID id,
        String name,
        String description,
        Double imdb,
        String categoryID,
        String genreID,
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
                aVideo.getCategoryID().getValue(),
                aVideo.getGenreID().getValue(),
                aVideo.isActive(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getDeletedAt()
        );
    }
}
