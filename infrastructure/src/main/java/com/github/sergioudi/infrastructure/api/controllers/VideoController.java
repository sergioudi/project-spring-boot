package com.github.sergioudi.infrastructure.api.controllers;

import com.github.sergioudi.application.video.create.CreateVideoCommand;
import com.github.sergioudi.application.video.create.CreateVideoOutput;
import com.github.sergioudi.application.video.create.CreateVideoUseCase;
import com.github.sergioudi.application.video.delete.DeleteVideoUseCase;
import com.github.sergioudi.application.video.retrieve.get.GetVideoByIdUseCase;
import com.github.sergioudi.application.video.retrieve.list.ListVideosUseCase;
import com.github.sergioudi.application.video.update.UpdateVideoCommand;
import com.github.sergioudi.application.video.update.UpdateVideoOutput;
import com.github.sergioudi.application.video.update.UpdateVideoUseCase;
import com.github.sergioudi.domain.pagination.Pagination;
import com.github.sergioudi.domain.pagination.SearchQuery;
import com.github.sergioudi.domain.validation.handler.Notification;
import com.github.sergioudi.infrastructure.api.VideoAPI;
import com.github.sergioudi.infrastructure.video.models.VideoListResponse;
import com.github.sergioudi.infrastructure.video.models.VideoResponse;
import com.github.sergioudi.infrastructure.video.models.CreateVideoRequest;
import com.github.sergioudi.infrastructure.video.models.UpdateVideoRequest;
import com.github.sergioudi.infrastructure.video.presenters.VideoApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class VideoController implements VideoAPI {

    private final CreateVideoUseCase createVideoUseCase;
    private final GetVideoByIdUseCase getVideoByIdUseCase;
    private final UpdateVideoUseCase updateVideoUseCase;
    private final DeleteVideoUseCase deleteVideoUseCase;
    private final ListVideosUseCase listVideosUseCase;

    public VideoController(
            final CreateVideoUseCase createVideoUseCase,
            final GetVideoByIdUseCase getVideoByIdUseCase,
            final UpdateVideoUseCase updateVideoUseCase,
            final DeleteVideoUseCase deleteVideoUseCase,
            final ListVideosUseCase listVideosUseCase
    ) {
        this.createVideoUseCase = Objects.requireNonNull(createVideoUseCase);
        this.getVideoByIdUseCase = Objects.requireNonNull(getVideoByIdUseCase);
        this.updateVideoUseCase = Objects.requireNonNull(updateVideoUseCase);
        this.deleteVideoUseCase = Objects.requireNonNull(deleteVideoUseCase);
        this.listVideosUseCase = Objects.requireNonNull(listVideosUseCase);
    }

    @Override
    public ResponseEntity<?> createVideo(final CreateVideoRequest input) {
        final var aCommand = CreateVideoCommand.with(
                input.name(),
                input.description(),
                input.imdb(),
                input.categoryId(),
                input.genreId(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateVideoOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/videos/" + output.id())).body(output);

        return this.createVideoUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<VideoListResponse> listVideos(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return listVideosUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(VideoApiPresenter::present);
    }

    @Override
    public VideoResponse getById(final String id) {
        return VideoApiPresenter.present(this.getVideoByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateVideoRequest input) {
        final var aCommand = UpdateVideoCommand.with(
                id,
                input.name(),
                input.description(),
                input.imdb(),
                input.categoryId(),
                input.genreId(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateVideoOutput, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return this.updateVideoUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteVideoUseCase.execute(anId);
    }
}
