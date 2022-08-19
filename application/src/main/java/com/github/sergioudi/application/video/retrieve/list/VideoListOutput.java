package com.github.sergioudi.application.video.retrieve.list;

import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoID;

import java.time.Instant;

public record VideoListOutput(
        VideoID id,
        String name,
        String description,
        Double imdb,
        String videoID,
        String genreID,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {

    public static VideoListOutput from(final Video aVideo) {
        return new VideoListOutput(
                aVideo.getId(),
                aVideo.getName(),
                aVideo.getDescription(),
                aVideo.getImdb(),
                aVideo.getCategoryID().getValue(),
                aVideo.getGenreID().getValue(),
                aVideo.isActive(),
                aVideo.getCreatedAt(),
                aVideo.getDeletedAt()
        );
    }
}
