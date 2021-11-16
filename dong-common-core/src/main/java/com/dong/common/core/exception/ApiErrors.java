package com.dong.common.core.exception;

public interface ApiErrors {
    int getStatus();

    String getErrorCode();

    String getErrorMessage();
}
