package com.funeat.member.exception;

import org.springframework.http.HttpStatus;

public enum MemberErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다. 회원 id를 확인하세요.", "5001"),
    MEMBER_UPDATE_ERROR(HttpStatus.BAD_REQUEST, "닉네임 또는 이미지를 확인하세요.", "5002"),
    MEMBER_DUPLICATE_FAVORITE(HttpStatus.CONFLICT, "이미 좋아요를 누른 상태입니다.", "5003"),
    MEMBER_DUPLICATE_BOOKMARK(HttpStatus.CONFLICT, "이미 북마크를 누른 상태입니다.", "5004"),
    ;

    private final HttpStatus status;
    private final String message;
    private final String code;

    MemberErrorCode(final HttpStatus status, final String message, final String code) {
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
