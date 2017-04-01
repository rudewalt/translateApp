package com.ivan.translateapp.data.net.exception;

/**
 * Created by Ivan on 31.03.2017.
 */

public class CanNotBeTranslatedException extends Exception {
    public CanNotBeTranslatedException(String message){
        super(message);
    }

    public CanNotBeTranslatedException(){
        super();
    }
}
