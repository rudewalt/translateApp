package com.ivan.translateapp.data.net.exception;

public class TranslateServiceException extends Exception {

    private final int errorCode;

    public TranslateServiceException(int errorCode){
        super();

        this.errorCode = errorCode;
    }

    public String getMessageResName(){
        return "error_title_code_"+ errorCode;
    }

    public String getDescriptionResName(){
        return "error_description_code_"+ errorCode;
    }
}
