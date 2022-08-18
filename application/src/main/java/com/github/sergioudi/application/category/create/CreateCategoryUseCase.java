package com.github.sergioudi.application.category.create;

import com.github.sergioudi.application.UseCase;
import com.github.sergioudi.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
        extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
