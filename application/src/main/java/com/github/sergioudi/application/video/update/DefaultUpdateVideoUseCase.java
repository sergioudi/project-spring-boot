package com.github.sergioudi.application.video.update;

import com.github.sergioudi.domain.category.CategoryGateway;
import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreGateway;
import com.github.sergioudi.domain.genre.GenreID;
import com.github.sergioudi.domain.validation.Error;
import com.github.sergioudi.domain.validation.ValidationHandler;
import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoGateway;
import com.github.sergioudi.domain.video.VideoID;
import com.github.sergioudi.domain.exceptions.DomainException;
import com.github.sergioudi.domain.exceptions.NotFoundException;
import com.github.sergioudi.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateVideoUseCase extends UpdateVideoUseCase {

    private final VideoGateway videoGateway;
    private final GenreGateway genreGateway;

    private final CategoryGateway categoryGateway;

    public DefaultUpdateVideoUseCase(final GenreGateway genreGateway,
                                     final VideoGateway videoGateway, CategoryGateway categoryGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Either<Notification, UpdateVideoOutput> execute(final UpdateVideoCommand aCommand) {
        final var anId = VideoID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var aImdb = aCommand.imdb();
        final var aCategoryId = aCommand.categoryID();
        final var genres = toGenreID(aCommand.genres());
        final var isActive = aCommand.isActive();

        final var aVideo = this.videoGateway.findById(anId)
                .orElseThrow(notFound(anId));

        final var notification = Notification.create();
        notification.append(validateGenres(genres));
        notification.append(validateCategory(aCategoryId));

        aVideo
                .update(aName, aDescription, aImdb, CategoryID.from(aCategoryId), genres, isActive)
                .validate(notification);

        return notification.hasError() ? Left(notification) : update(aVideo);
    }

    private ValidationHandler validateGenres(final List<GenreID> ids) {
        final var notification = Notification.create();
        if (ids == null || ids.isEmpty()) {
            return notification;
        }

        final var retrievedIds = genreGateway.existsByIds(ids);

        if (ids.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMessage = missingIds.stream()
                    .map(GenreID::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new Error("Some genres could not be found: %s".formatted(missingIdsMessage)));
        }

        return notification;
    }

    private ValidationHandler validateCategory(final String id) {
        final var notification = Notification.create();
        if (id == null) {
            return notification;
        }

        final var retrievedId = categoryGateway.findById(CategoryID.from(id));

        if (!retrievedId.isPresent()) {

            notification.append(new Error("Category not be found: %s".formatted(id)));
        }

        return notification;
    }

    private Either<Notification, UpdateVideoOutput> update(final Video aVideo) {
        return Try(() -> this.videoGateway.update(aVideo))
                .toEither()
                .bimap(Notification::create, UpdateVideoOutput::from);
    }

    private Supplier<DomainException> notFound(final VideoID anId) {
        return () -> NotFoundException.with(Video.class, anId);
    }

    private List<GenreID> toGenreID(final List<String> genres) {
        return genres.stream()
                .map(GenreID::from)
                .toList();
    }
}
