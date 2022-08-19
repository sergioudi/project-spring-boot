package com.github.sergioudi.application.video.create;

import com.github.sergioudi.application.UseCase;
import com.github.sergioudi.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateVideoUseCase
        extends UseCase<CreateVideoCommand, Either<Notification, CreateVideoOutput>> {
}
