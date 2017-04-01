package com.ivan.translateapp.data.net.yandex;

/**
 * Created by Ivan on 31.03.2017.
 */

public class ResponseCodes {
    public static final int SUCCESS_CODE = 200;

    public static final int INVALID_API_KEY = 401;
    public static final int BLOCKED_API_KEY = 402;
    public static final int DAILY_LIMIT_EXCEEDED = 404;
    public static final int TEXT_LENGTH_LIMIT_EXCEEDED = 413;
    public static final int CANT_TRANSLATE = 422;
    public static final int UNSUPPORTED_DIRECTION = 501;

    public static String getErrorMessage(Integer code){
        switch (code){
            case INVALID_API_KEY:
                return "Неправильный API-ключ";
            case BLOCKED_API_KEY:
                return "API-ключ заблокирован";
            case DAILY_LIMIT_EXCEEDED:
                return "Превышено суточное ограничение на объем переведенного текста";
            case TEXT_LENGTH_LIMIT_EXCEEDED:
                return "Превышен максимально допустимый размер текста";
            case CANT_TRANSLATE:
                return "Текст не может быть переведен";
            case UNSUPPORTED_DIRECTION:
                return "Заданное направление перевода не поддерживается";
            default:
                return "";
        }
    }
}
