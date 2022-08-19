package com.github.sergioudi.application.video.update;

import com.github.sergioudi.application.UseCase;
import com.github.sergioudi.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateVideoUseCase
        extends UseCase<UpdateVideoCommand, Either<Notification, UpdateVideoOutput>> {
}
