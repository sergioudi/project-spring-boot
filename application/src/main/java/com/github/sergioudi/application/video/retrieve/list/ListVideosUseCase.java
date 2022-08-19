package com.github.sergioudi.application.video.retrieve.list;

import com.github.sergioudi.application.UseCase;
import com.github.sergioudi.domain.pagination.Pagination;
import com.github.sergioudi.domain.pagination.SearchQuery;

public abstract class ListVideosUseCase
        extends UseCase<SearchQuery, Pagination<VideoListOutput>> {
}
