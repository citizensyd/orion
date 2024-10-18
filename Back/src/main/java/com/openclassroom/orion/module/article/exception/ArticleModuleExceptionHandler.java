package com.openclassroom.orion.module.article.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;

@ControllerAdvice(basePackages = "com.openclassroom.orion.module.article")
public class ArticleModuleExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ArticleNotFoundException.class)
    protected ResponseEntity<Object> handleArticleNotFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, null, HttpStatus.NOT_FOUND, request);
    }

}
