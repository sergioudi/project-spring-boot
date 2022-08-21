package com.github.sergioudi.application.video.create;

import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreGateway;
import com.github.sergioudi.domain.genre.GenreID;
import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoGateway;
import com.github.sergioudi.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.List;
import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateVideoUseCase extends CreateVideoUseCase {

    private final VideoGateway videoGateway;
    private final GenreGateway genreGateway;

    public DefaultCreateVideoUseCase(final GenreGateway genreGateway,
                                     final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public Either<Notification, CreateVideoOutput> execute(final CreateVideoCommand aCommand) {
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var aImdb = aCommand.imdb();
        final var aCategoryId = aCommand.categoryID();
        final var genres = toGenreID(aCommand.genres());
        final var isActive = aCommand.isActive();

        final var notification = Notification.create();

        final var aVideo = Video.newVideo(aName, aDescription, aImdb, CategoryID.from(aCategoryId), isActive);
        aVideo.validate(notification);

        aVideo.addGenres(genres);

        return notification.hasError() ? Left(notification) : create(aVideo);
    }

    private Either<Notification, CreateVideoOutput> create(final Video aVideo) {
        return Try(() -> this.videoGateway.create(aVideo))
                .toEither()
                .bimap(Notification::create, CreateVideoOutput::from);
    }

    private List<GenreID> toGenreID(final List<String> genres) {
        return genres.stream()
                .map(GenreID::from)
                .toList();
    }
}
