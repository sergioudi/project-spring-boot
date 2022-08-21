package com.github.sergioudi.infrastructure.video.persistence;

import com.github.sergioudi.domain.genre.GenreID;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "videos_genres")
public class VideoGenreJpaEntity {

    @EmbeddedId
    private VideoGenreID id;

    @ManyToOne
    @MapsId("videoId")
    private VideoJpaEntity video;

    public VideoGenreJpaEntity() {}

    private VideoGenreJpaEntity(final VideoJpaEntity aVideo, final GenreID aGenreId) {
        this.id = VideoGenreID.from(aVideo.getId(), aGenreId.getValue());
        this.video = aVideo;
    }

    public static VideoGenreJpaEntity from(final VideoJpaEntity aVideo, final GenreID aGenreId) {
        return new VideoGenreJpaEntity(aVideo, aGenreId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoGenreJpaEntity that = (VideoGenreJpaEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public VideoGenreID getId() {
        return id;
    }

    public VideoGenreJpaEntity setId(VideoGenreID id) {
        this.id = id;
        return this;
    }

    public VideoJpaEntity getVideo() {
        return video;
    }

    public VideoGenreJpaEntity setVideo(VideoJpaEntity video) {
        this.video = video;
        return this;
    }
}
