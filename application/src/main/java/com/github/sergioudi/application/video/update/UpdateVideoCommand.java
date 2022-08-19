package com.github.sergioudi.application.video.update;

public record UpdateVideoCommand(
        String id,
        String name,
        String description,
        Double imdb,
        String categoryID,
        String genreID,
        boolean isActive
) {

    public static UpdateVideoCommand with(
            final String anId,
            final String aName,
            final String aDescription,
            final Double aImdb,
            final String aCategoryID,
            final String aGenreID,
            final boolean isActive
    ) {
        return new UpdateVideoCommand(anId, aName, aDescription, aImdb, aCategoryID, aGenreID, isActive);
    }
}
