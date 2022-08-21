package com.github.sergioudi.infrastructure.configuration.usecases;

import com.github.sergioudi.application.video.create.CreateVideoUseCase;
import com.github.sergioudi.application.video.create.DefaultCreateVideoUseCase;
import com.github.sergioudi.application.video.delete.DefaultDeleteVideoUseCase;
import com.github.sergioudi.application.video.delete.DeleteVideoUseCase;
import com.github.sergioudi.application.video.retrieve.get.DefaultGetVideoByIdUseCase;
import com.github.sergioudi.application.video.retrieve.get.GetVideoByIdUseCase;
import com.github.sergioudi.application.video.retrieve.list.DefaultListVideosUseCase;
import com.github.sergioudi.application.video.retrieve.list.ListVideosUseCase;
import com.github.sergioudi.application.video.update.DefaultUpdateVideoUseCase;
import com.github.sergioudi.application.video.update.UpdateVideoUseCase;
import com.github.sergioudi.domain.genre.GenreGateway;
import com.github.sergioudi.domain.video.VideoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VideoUseCaseConfig {

    private final VideoGateway videoGateway;
    private final GenreGateway genreGateway;

    public VideoUseCaseConfig(final GenreGateway genreGateway,final VideoGateway videoGateway) {
        this.videoGateway = videoGateway;
        this.genreGateway = genreGateway;
    }

    @Bean
    public CreateVideoUseCase createVideoUseCase() {
        return new DefaultCreateVideoUseCase(genreGateway,videoGateway);
    }

    @Bean
    public UpdateVideoUseCase updateVideoUseCase() {
        return new DefaultUpdateVideoUseCase(genreGateway,videoGateway);
    }

    @Bean
    public GetVideoByIdUseCase getVideoByIdUseCase() {
        return new DefaultGetVideoByIdUseCase(videoGateway);
    }

    @Bean
    public ListVideosUseCase listVideosUseCase() {
        return new DefaultListVideosUseCase(videoGateway);
    }

    @Bean
    public DeleteVideoUseCase deleteVideoUseCase() {
        return new DefaultDeleteVideoUseCase(videoGateway);
    }
}
