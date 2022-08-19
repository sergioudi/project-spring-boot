package com.github.sergioudi.domain.video;

import com.github.sergioudi.domain.validation.Error;
import com.github.sergioudi.domain.validation.ValidationHandler;
import com.github.sergioudi.domain.validation.Validator;

public class VideoValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 3;
    private final Video Video;

    public VideoValidator(final Video aVideo, final ValidationHandler aHandler) {
        super(aHandler);
        this.Video = aVideo;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.Video.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
