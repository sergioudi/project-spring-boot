package com.github.sergioudi.infrastructure.video.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VideoGenreID implements Serializable {

    @Column(name = "video_id", nullable = false)
    private String videoId;

    @Column(name = "genre_id", nullable = false)
    private String genreId;

    public VideoGenreID() {}

    private VideoGenreID(final String aVideoId, final String aGenreId) {
        this.videoId = aVideoId;
        this.genreId = aGenreId;
    }

    public static VideoGenreID from(final String aVideoId, final String aGenreId) {
        return new VideoGenreID(aVideoId, aGenreId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoGenreID that = (VideoGenreID) o;
        return Objects.equals(getVideoId(), that.getVideoId()) && Objects.equals(getGenreId(), that.getGenreId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVideoId(), getGenreId());
    }

    public String getVideoId() {
        return videoId;
    }

    public VideoGenreID setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }

    public String getGenreId() {
        return genreId;
    }

    public VideoGenreID setGenreId(String genreId) {
        this.genreId = genreId;
        return this;
    }
}
