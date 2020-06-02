package com.abouerp.textbook.exception;

/**
 * @author Abouerp
 */
public class TextBookNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "TextBook Not Found";

    public TextBookNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
