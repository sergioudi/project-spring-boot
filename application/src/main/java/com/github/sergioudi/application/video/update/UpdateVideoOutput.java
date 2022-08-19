package com.github.sergioudi.application.video.update;

import com.github.sergioudi.domain.video.Video;

public record UpdateVideoOutput(
        String id
) {

    public static UpdateVideoOutput from(final String anId) {
        return new UpdateVideoOutput(anId);
    }

    public static UpdateVideoOutput from(final Video aVideo) {
        return new UpdateVideoOutput(aVideo.getId().getValue());
    }
}
