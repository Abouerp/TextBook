package com.abouerp.textbook.exception;

/**
 * @author Abouerp
 */
public class CollegeNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "College Not Found";

    public CollegeNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
