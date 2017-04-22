package com.ivan.translateapp.data.net.yandex.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageResponse extends BaseResponse {
    @SerializedName("lang")
    @Expose
    private String lang;


    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
