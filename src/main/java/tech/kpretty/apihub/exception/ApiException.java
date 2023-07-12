package tech.kpretty.apihub.exception;

import lombok.Data;


public class ApiException extends Exception {

    public ApiException(String errorMsg) {
        super(errorMsg);
    }
}
