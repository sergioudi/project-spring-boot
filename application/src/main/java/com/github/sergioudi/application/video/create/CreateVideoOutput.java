package com.github.sergioudi.application.video.create;

import com.github.sergioudi.domain.video.Video;

public record CreateVideoOutput(
        String id
) {

    public static CreateVideoOutput from(final String anId) {
        return new CreateVideoOutput(anId);
    }

    public static CreateVideoOutput from(final Video aVideo) {
        return new CreateVideoOutput(aVideo.getId().getValue());
    }
}
