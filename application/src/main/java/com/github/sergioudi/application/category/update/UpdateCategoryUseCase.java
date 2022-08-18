package com.github.sergioudi.application.category.update;

import com.github.sergioudi.application.UseCase;
import com.github.sergioudi.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
