package com.github.sergioudi.domain.video;

import com.github.sergioudi.domain.pagination.Pagination;
import com.github.sergioudi.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface VideoGateway {

    Video create(Video aVideo);

    void deleteById(VideoID anId);

    Optional<Video> findById(VideoID anId);

    Video update(Video aVideo);

    Pagination<Video> findAll(SearchQuery aQuery);

    List<VideoID> existsByIds(Iterable<VideoID> ids);
}
