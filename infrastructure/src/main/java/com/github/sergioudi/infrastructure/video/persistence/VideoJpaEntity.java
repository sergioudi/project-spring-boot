package com.github.sergioudi.infrastructure.video.persistence;

import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreID;
import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity(name = "Video")
@Table(name = "video")
public class VideoJpaEntity {

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 4000)
    private String description;

    @Column(name = "imdb", nullable = false)
    private Double imdb;

    @Column(name = "category_id", nullable = false)
    private String categoryID;

    @Column(name = "genre_id", nullable = false)
    private String genreID;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @Column(name = "deleted_at", columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    public VideoJpaEntity() {
    }

    private VideoJpaEntity(
            final String id,
            final String name,
            final String description,
            final Double imdb,
            final String categoryID,
            final String genreID,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imdb = imdb;
        this.categoryID = categoryID;
        this.genreID = genreID;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static VideoJpaEntity from(final Video aVideo) {
        return new VideoJpaEntity(
                aVideo.getId().getValue(),
                aVideo.getName(),
                aVideo.getDescription(),
                aVideo.getImdb(),
                aVideo.getCategoryID().getValue(),
                aVideo.getGenreID().getValue(),
                aVideo.isActive(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getDeletedAt()
        );
    }

    public Video toAggregate() {
        return Video.with(
                VideoID.from(getId()),
                getName(),
                getDescription(),
                getImdb(),
                CategoryID.from(getCategoryID()),
                GenreID.from(getGenreID()),
                isActive(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getImdb() { return imdb; }

    public String getCategoryID() { return categoryID;  }

    public String getGenreID() { return genreID; }

    public void setImdb(Double imdb) { this.imdb = imdb; }

    public void setCategoryID(String categoryID) { this.categoryID = categoryID; }

    public void setGenreID(String genreID) { this.genreID = genreID; }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}
