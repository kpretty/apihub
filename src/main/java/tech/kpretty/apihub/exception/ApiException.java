package tech.kpretty.apihub.exception;


public class ApiException extends Exception {

    public ApiException(String errorMsg) {
        super(errorMsg);
    }
}
