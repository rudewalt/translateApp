package com.ivan.translateapp.data.dto;

/**
 * Created by Ivan on 26.03.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageDTO {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("lang")
    @Expose
    private String lang;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
