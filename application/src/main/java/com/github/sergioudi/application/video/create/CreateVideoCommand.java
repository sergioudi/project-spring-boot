package com.github.sergioudi.application.video.create;

import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreID;

import java.util.List;

public record CreateVideoCommand(
        String name,
        String description,
        Double imdb,
        String categoryID,
        List<String> genres,
        boolean isActive
) {

    public static CreateVideoCommand with(
            final String aName,
            final String aDescription,
            final Double aImdb,
            final String aCategoryID,
            final List<String> genres,
            final boolean isActive
    ) {
        return new CreateVideoCommand(aName, aDescription, aImdb, aCategoryID, genres, isActive);
    }
}
