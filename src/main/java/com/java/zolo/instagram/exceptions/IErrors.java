package com.java.zolo.instagram.exceptions;

import com.java.zolo.instagram.validators.Validator;
import com.zolo.alpha.exceptionhandling.Error;

public class IErrors {

    public static final Error INTERNAL_SERVER_ERROR = new Error("A server error occurred.", 1000);

    public static final Error INVALID_REQUEST = new Error("Invalid request", 1001);

    public static final Error INVALID_RESPONSE = new Error("Invalid response", 1002);

    public static final Error FAILED_TO_CREATE_USER = new Error("Failed to create User",1006);

    public static final Error PARSING_ERROR = new Error("Error while parsing response",  1008);


    public static <T> Error getSettlementError(Validator<T> validator) {
        return new Error(validator.getFailureMessage(),  1009);
    }

}
