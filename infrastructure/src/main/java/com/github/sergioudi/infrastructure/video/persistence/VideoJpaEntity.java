package com.github.sergioudi.infrastructure.video.persistence;

import com.github.sergioudi.domain.category.Category;
import com.github.sergioudi.domain.category.CategoryID;
import com.github.sergioudi.domain.genre.GenreID;
import com.github.sergioudi.domain.video.Video;
import com.github.sergioudi.domain.video.VideoID;
import com.github.sergioudi.infrastructure.category.persistence.CategoryJpaEntity;


import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

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

    @ManyToOne
    @MapsId("categoryID")
    private CategoryJpaEntity category;

    @OneToMany(mappedBy = "video", cascade = ALL, fetch = EAGER, orphanRemoval = true)
    private Set<VideoGenreJpaEntity> genres;

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
        this.genres = new HashSet<>();;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static VideoJpaEntity from(final Video aVideo) {
        final var anEntity = new VideoJpaEntity(
                aVideo.getId().getValue(),
                aVideo.getName(),
                aVideo.getDescription(),
                aVideo.getImdb(),
                aVideo.getCategoryID().getValue(),
                aVideo.isActive(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getDeletedAt()
        );

        aVideo.getGenres()
                .forEach(anEntity::addGenre);

        return anEntity;
    }

    public Video toAggregate() {
        return Video.with(
                VideoID.from(getId()),
                getName(),
                getDescription(),
                getImdb(),
                CategoryID.from(getCategoryID()),
                getGenreIDs(),
                isActive(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    private void addGenre(final GenreID anId) {
        this.genres.add(VideoGenreJpaEntity.from(this, anId));
    }

    private void removeCategory(final GenreID anId) {
        this.genres.remove(VideoGenreJpaEntity.from(this, anId));
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

    public List<GenreID> getGenreIDs() {
        return getGenres().stream()
                .map(it -> GenreID.from(it.getId().getGenreId()))
                .toList();
    }

    public Set<VideoGenreJpaEntity> getGenres() {
        return genres;
    }

    public void setImdb(Double imdb) { this.imdb = imdb; }

    public void setCategoryID(String categoryID) { this.categoryID = categoryID; }

    public VideoJpaEntity setGenres(Set<VideoGenreJpaEntity> genres) {
        this.genres = genres;
        return this;
    }

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

    public CategoryJpaEntity getCategory() {
        return category;
    }
}
