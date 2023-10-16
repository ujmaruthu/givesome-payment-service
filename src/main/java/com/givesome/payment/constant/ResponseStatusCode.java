package com.givesome.payment.constant;

public class ResponseStatusCode {
    private ResponseStatusCode() {

    }
    public static final Integer SUCCESS= 200;
    public static final Integer BAD_REQUEST= 400;
    public static final Integer ALREADY_FOUND_EXCEPTION= 403;
    public static final Integer INTERNAL_SERVER_ERROR= 500;
    public static final Integer NOT_FOUND_EXCEPTION= 404;

}
