package com.github.sergioudi.domain.video;

import com.github.sergioudi.domain.AggregateRoot;
import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.Genre;
import com.github.sergioudi.domain.genre.GenreID;
import com.github.sergioudi.domain.utils.InstantUtils;
import com.github.sergioudi.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Video extends AggregateRoot<VideoID> implements Cloneable {
    private String name;
    private String description;
    private Double imdb;
    private CategoryID categoryID;
    private List<GenreID> genres;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Video(
            final VideoID anId,
            final String aName,
            final String aDescription,
            final Double aImdb,
            final CategoryID aCategoryID,
            final List<GenreID> genres,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeleteDate
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.imdb = aImdb;
        this.categoryID = aCategoryID;
        this.genres = genres;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(aCreationDate, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdateDate, "'updatedAt' should not be null");
        this.deletedAt = aDeleteDate;
    }

    public static Video newVideo(final String aName, final String aDescription, Double aImdb,CategoryID aCategoryID,final boolean isActive) {
        final var id = VideoID.unique();
        final var now = InstantUtils.now();
        final var deletedAt = isActive ? null : now;
        return new Video(id, aName, aDescription, aImdb, aCategoryID, new ArrayList<>(), isActive, now, now, deletedAt);
    }

    public static Video with(
            final VideoID anId,
            final String name,
            final String description,
            final Double aImdb,
            final CategoryID aCategoryID,
            final List<GenreID> genres,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Video(
                anId,
                name,
                description,
                aImdb,
                aCategoryID,
                genres,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Video with(final Video aVideo) {
        return with(
                aVideo.getId(),
                aVideo.name,
                aVideo.description,
                aVideo.imdb,
                aVideo.categoryID,
                aVideo.genres,
                aVideo.isActive(),
                aVideo.createdAt,
                aVideo.updatedAt,
                aVideo.deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new VideoValidator(this, handler).validate();
    }

    public Video activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Video deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = InstantUtils.now();
        }

        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Video update(
            final String aName,
            final String aDescription,
            final Double aImdb,
            final CategoryID aCategoryID,
            final List<GenreID> genres,
            final boolean isActive
    ) {
        if (isActive) {
            activate();
        } else {
            deactivate();
        }
        this.name = aName;
        this.description = aDescription;
        this.imdb = aImdb;
        this.categoryID = aCategoryID;
        this.genres = genres;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public VideoID getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public Double getImdb() {
        return imdb;
    }

    public CategoryID getCategoryID() {
        return categoryID;
    }

    public List<GenreID> getGenres() {
        return Collections.unmodifiableList(genres);
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public Video clone() {
        try {
            return (Video) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Video addGenre(final GenreID aGenreID) {
        if (aGenreID == null) {
            return this;
        }
        this.genres.add(aGenreID);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Video addGenres(final List<GenreID> genres) {
        if (genres == null || genres.isEmpty()) {
            return this;
        }
        this.genres.addAll(genres);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Video removeGenre(final GenreID aGenreID) {
        if (aGenreID == null) {
            return this;
        }
        this.genres.remove(aGenreID);
        this.updatedAt = InstantUtils.now();
        return this;
    }
}
