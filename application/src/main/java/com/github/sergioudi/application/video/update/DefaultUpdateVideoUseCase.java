package com.github.sergioudi.application.video.update;

import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreID;
import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoGateway;
import com.github.sergioudi.domain.video.VideoID;
import com.github.sergioudi.domain.exceptions.DomainException;
import com.github.sergioudi.domain.exceptions.NotFoundException;
import com.github.sergioudi.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateVideoUseCase extends UpdateVideoUseCase {

    private final VideoGateway videoGateway;

    public DefaultUpdateVideoUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public Either<Notification, UpdateVideoOutput> execute(final UpdateVideoCommand aCommand) {
        final var anId = VideoID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var aImdb = aCommand.imdb();
        final var aCategoryId = aCommand.categoryID();
        final var aGenreId = aCommand.genreID();
        final var isActive = aCommand.isActive();

        final var aVideo = this.videoGateway.findById(anId)
                .orElseThrow(notFound(anId));

        final var notification = Notification.create();
        aVideo
                .update(aName, aDescription, aImdb, CategoryID.from(aCategoryId), GenreID.from(aGenreId), isActive)
                .validate(notification);

        return notification.hasError() ? Left(notification) : update(aVideo);
    }

    private Either<Notification, UpdateVideoOutput> update(final Video aVideo) {
        return Try(() -> this.videoGateway.update(aVideo))
                .toEither()
                .bimap(Notification::create, UpdateVideoOutput::from);
    }

    private Supplier<DomainException> notFound(final VideoID anId) {
        return () -> NotFoundException.with(Video.class, anId);
    }
}
