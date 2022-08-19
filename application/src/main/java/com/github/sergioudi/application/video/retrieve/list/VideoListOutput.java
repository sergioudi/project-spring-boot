package com.github.sergioudi.application.video.retrieve.list;

import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreID;
import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoID;

import java.time.Instant;

public record VideoListOutput(
        VideoID id,
        String name,
        String description,
        Double imdb,
        CategoryID categoryID,
        GenreID genreID,
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
                aVideo.getCategoryID(),
                aVideo.getGenreID(),
                aVideo.isActive(),
                aVideo.getCreatedAt(),
                aVideo.getDeletedAt()
        );
    }
}
