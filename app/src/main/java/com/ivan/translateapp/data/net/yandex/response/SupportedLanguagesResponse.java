package com.ivan.translateapp.data.net.yandex.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 26.03.2017.
 */

public class SupportedLanguagesResponse extends BaseResponse {

    @SerializedName("dirs")
    @Expose
    private List<String> dirs = new ArrayList<>();

    public Map<String, String> getLangs() {
        return langs;
    }

    public void setLangs(Map<String, String> langs) {
        this.langs = langs;
    }

    @SerializedName("langs")
    Map<String, String> langs = new HashMap<>();

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

}