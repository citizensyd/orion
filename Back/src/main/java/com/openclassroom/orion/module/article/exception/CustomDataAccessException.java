package com.openclassroom.orion.module.article.exception;

import org.springframework.dao.DataAccessException;

public class CustomDataAccessException extends DataAccessException {

    public CustomDataAccessException(String msg) {
        super(msg);
    }
    public CustomDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
