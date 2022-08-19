package com.github.sergioudi.domain.video;

import com.github.sergioudi.domain.AggregateRoot;
import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreID;
import com.github.sergioudi.domain.utils.InstantUtils;
import com.github.sergioudi.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Video extends AggregateRoot<VideoID> implements Cloneable {
    private String name;
    private String description;
    private Double imdb;
    private CategoryID categoryID;
    private GenreID genreID;
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
            final GenreID aGenreID,
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
        this.genreID = aGenreID;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(aCreationDate, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdateDate, "'updatedAt' should not be null");
        this.deletedAt = aDeleteDate;
    }

    public static Video newVideo(final String aName, final String aDescription, Double aImdb,CategoryID aCategoryID, GenreID aGenreID , final boolean isActive) {
        final var id = VideoID.unique();
        final var now = InstantUtils.now();
        final var deletedAt = isActive ? null : now;
        return new Video(id, aName, aDescription, aImdb, aCategoryID, aGenreID, isActive, now, now, deletedAt);
    }

    public static Video with(
            final VideoID anId,
            final String name,
            final String description,
            final Double aImdb,
            final CategoryID aCategoryID,
            final GenreID aGenreID,
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
                aGenreID,
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
                aVideo.genreID,
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
            final GenreID aGenreID,
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
        this.genreID = aGenreID;
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

    public GenreID getGenreID() {
        return genreID;
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
}
