package com.reactive.spring.playgroung.error_handling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends Throwable {

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public CustomException(Throwable e) {
        this.message = e.getMessage();
    }
}
