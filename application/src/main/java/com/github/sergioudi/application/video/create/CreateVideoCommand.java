package com.github.sergioudi.application.video.create;

import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreID;

public record CreateVideoCommand(
        String name,
        String description,
        Double imdb,
        String categoryID,
        String genreID,
        boolean isActive
) {

    public static CreateVideoCommand with(
            final String aName,
            final String aDescription,
            final Double aImdb,
            final String aCategoryID,
            final String aGenreID,
            final boolean isActive
    ) {
        return new CreateVideoCommand(aName, aDescription, aImdb, aCategoryID, aGenreID, isActive);
    }
}
