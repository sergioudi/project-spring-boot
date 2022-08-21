CREATE TABLE videos_genres (
    video_id VARCHAR(36) NOT NULL,
    genre_id VARCHAR(36) NOT NULL,
    CONSTRAINT idx_video_genre UNIQUE (video_id, genre_id),
    CONSTRAINT fk_genre_videos_id FOREIGN KEY (genre_id) REFERENCES genres (id) ON DELETE CASCADE,
    CONSTRAINT fk_video_videos_id FOREIGN KEY (video_id) REFERENCES video (id) ON DELETE CASCADE
);