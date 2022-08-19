package com.github.sergioudi.application.video.retrieve.get;

import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoGateway;
import com.github.sergioudi.domain.video.VideoID;
import com.github.sergioudi.domain.exceptions.NotFoundException;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetVideoByIdUseCase extends GetVideoByIdUseCase {

    private final VideoGateway videoGateway;

    public DefaultGetVideoByIdUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public VideoOutput execute(final String anIn) {
        final var anVideoID = VideoID.from(anIn);

        return this.videoGateway.findById(anVideoID)
                .map(VideoOutput::from)
                .orElseThrow(notFound(anVideoID));
    }

    private Supplier<NotFoundException> notFound(final VideoID anId) {
        return () -> NotFoundException.with(Video.class, anId);
    }
}
