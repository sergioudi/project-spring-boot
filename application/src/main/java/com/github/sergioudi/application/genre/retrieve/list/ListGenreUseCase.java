package com.github.sergioudi.application.genre.retrieve.list;

import com.github.sergioudi.application.UseCase;
import com.github.sergioudi.domain.pagination.Pagination;
import com.github.sergioudi.domain.pagination.SearchQuery;

public abstract class ListGenreUseCase
        extends UseCase<SearchQuery, Pagination<GenreListOutput>> {
}
