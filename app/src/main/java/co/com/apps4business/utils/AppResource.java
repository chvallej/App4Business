package co.com.apps4business.utils;

import android.content.Context;

public class AppResource {
    public static String string(Context context, int res_id) {
        return context.getResources().getString(res_id);
    }

    public static Integer dimen(Context context, int res_id) {
        return (int) context.getResources().getDimension(res_id);
    }

    public static int color(Context context, int res_id) {
        return context.getResources().getColor(res_id);
    }
}