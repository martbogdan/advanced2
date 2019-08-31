package com.advanced.taracat.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExeption extends RuntimeException {
    public NotFoundExeption() {
        super("RESOURCE NOT FOUND!!!");
    }
}
