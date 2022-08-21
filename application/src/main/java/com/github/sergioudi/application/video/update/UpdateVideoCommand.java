package com.github.sergioudi.application.video.update;

import java.util.List;

public record UpdateVideoCommand(
        String id,
        String name,
        String description,
        Double imdb,
        String categoryID,
        List<String> genres,
        boolean isActive
) {

    public static UpdateVideoCommand with(
            final String anId,
            final String aName,
            final String aDescription,
            final Double aImdb,
            final String aCategoryID,
            final List<String> genres,
            final boolean isActive
    ) {
        return new UpdateVideoCommand(anId, aName, aDescription, aImdb, aCategoryID, genres, isActive);
    }
}
