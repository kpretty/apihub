package tech.kpretty.apihub.result;

public class ResultResponse {
    private static final Integer SUCCESS_CODE = 200;
    private static final String SUCCESS_MSG = "success";
    private static final Integer ERROR_CODE = 500;
    private static final String ERROR_MSG = "internal error";

    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        return new Result(SUCCESS_CODE, SUCCESS_MSG, null, data);
    }

    public static Result success(MetaData metaData, Object data) {
        return new Result(SUCCESS_CODE, SUCCESS_MSG, metaData, data);
    }

    private static Result error() {
        return new Result(ERROR_CODE, ERROR_MSG, null, null);
    }

    public static Result error(String msg) {
        return new Result(ERROR_CODE, msg, null, null);
    }

    public static Result error(String msg, Object data) {
        return new Result(ERROR_CODE, msg, null, data);
    }


}
