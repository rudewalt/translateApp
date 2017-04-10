package com.ivan.translateapp.data.db.entity;

/**
 * Created by Ivan on 10.04.2017.
 */

public class KeyValueEntity {
    private String key;
    private String value;

    public KeyValueEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
