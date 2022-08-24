package com.github.sergioudi.domain.genre;

import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.pagination.Pagination;
import com.github.sergioudi.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface GenreGateway {

    Genre create(Genre aGenre);

    void deleteById(GenreID anId);

    Optional<Genre> findById(GenreID anId);

    Genre update(Genre aGenre);

    Pagination<Genre> findAll(SearchQuery aQuery);
    List<GenreID> existsByIds(Iterable<GenreID> ids);
}
