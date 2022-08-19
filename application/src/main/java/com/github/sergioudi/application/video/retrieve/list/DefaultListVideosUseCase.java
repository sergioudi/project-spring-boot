package com.github.sergioudi.application.video.retrieve.list;

import com.github.sergioudi.domain.video.VideoGateway;
import com.github.sergioudi.domain.pagination.Pagination;
import com.github.sergioudi.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListVideosUseCase extends ListVideosUseCase {

    private final VideoGateway videoGateway;

    public DefaultListVideosUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public Pagination<VideoListOutput> execute(final SearchQuery aQuery) {
        return this.videoGateway.findAll(aQuery)
                .map(VideoListOutput::from);
    }
}
