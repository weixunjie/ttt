package com.face.backend.exception;

import com.face.backend.pojo.SearchResultInfo;

public class BusinessException extends RuntimeException{
    private SearchResultInfo errorInfo;

    public SearchResultInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(SearchResultInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public BusinessException(SearchResultInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public BusinessException() {
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "errorInfo=" + errorInfo +
                '}';
    }
}
