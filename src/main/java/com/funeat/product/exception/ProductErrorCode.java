package com.funeat.product.exception;

import org.springframework.http.HttpStatus;

public enum ProductErrorCode {

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다. 상품 id를 확인하세요.", "1001"),
    NOT_SUPPORTED_PRODUCT_SORTING_CONDITION(HttpStatus.BAD_REQUEST, "정렬 조건이 올바르지 않습니다. 정렬 조건을 확인하세요", "1002");
    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    ProductErrorCode(final HttpStatus status, final String message, final String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
