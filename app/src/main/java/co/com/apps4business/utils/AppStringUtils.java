package co.com.apps4business.utils;

import java.util.Arrays;

public class AppStringUtils {

    public static String[] getArray(Class<? extends Enum<?>> e) {
        return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
    }


}
