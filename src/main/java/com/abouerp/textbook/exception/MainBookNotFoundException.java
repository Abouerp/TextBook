package com.abouerp.textbook.exception;

/**
 * @author Abouerp
 */
public class MainBookNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "MainBook Not Found";

    public MainBookNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
