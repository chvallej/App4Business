package co.com.apps4business.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

    public static Gson createGsonFromBuilder(){
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        gsonbuilder.excludeFieldsWithoutExposeAnnotation();
        return gsonbuilder.serializeNulls().create();
    }

    public static Gson createGsonFromBuilderwithExclusion(){
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return gsonbuilder.serializeNulls().create();
    }
}
