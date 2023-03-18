package org.octri.omop_annotator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Action Forbidden")
public class ForbiddenException extends Exception {

    public ForbiddenException(String message) {
        super(message);
    }

}
