package com.github.sergioudi.application.video.create;

import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreID;
import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoGateway;
import com.github.sergioudi.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateVideoUseCase extends CreateVideoUseCase {

    private final VideoGateway videoGateway;

    public DefaultCreateVideoUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public Either<Notification, CreateVideoOutput> execute(final CreateVideoCommand aCommand) {
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var aImdb = aCommand.imdb();
        final var aCategoryId = aCommand.categoryID();
        final var aGenreId = aCommand.genreID();
        final var isActive = aCommand.isActive();

        final var notification = Notification.create();

        final var aVideo = Video.newVideo(aName, aDescription, aImdb, CategoryID.from(aCategoryId), GenreID.from(aGenreId),  isActive);
        aVideo.validate(notification);

        return notification.hasError() ? Left(notification) : create(aVideo);
    }

    private Either<Notification, CreateVideoOutput> create(final Video aVideo) {
        return Try(() -> this.videoGateway.create(aVideo))
                .toEither()
                .bimap(Notification::create, CreateVideoOutput::from);
    }
}
