package com.github.sergioudi.application.category.retrieve.list;

import com.github.sergioudi.application.UseCase;
import com.github.sergioudi.domain.pagination.SearchQuery;
import com.github.sergioudi.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase
        extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {
}
