package com.github.sergioudi.infrastructure.video.presenters;

import com.github.sergioudi.application.video.retrieve.get.VideoOutput;
import com.github.sergioudi.application.video.retrieve.list.VideoListOutput;
import com.github.sergioudi.infrastructure.video.models.VideoListResponse;
import com.github.sergioudi.infrastructure.video.models.VideoResponse;

public interface VideoApiPresenter {

    static VideoResponse present(final VideoOutput output) {
        return new VideoResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.imdb(),
                output.categoryID().getValue(),
                output.genreID().getValue(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static VideoListResponse present(final VideoListOutput output) {
        return new VideoListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.imdb(),
                output.categoryID().getValue(),
                output.genreID().getValue(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
