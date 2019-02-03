package co.com.apps4business.rpc;

import android.content.Context;

public class AppInstance extends AppWrapper<AppInstance> {

    public AppInstance(Context context, String baseURL) {
        super(context, baseURL);
    }

    public static AppInstance createInstance(Context context, String baseURL) {
        AppInstance appInstance =  new AppInstance(context, baseURL);
        return appInstance;
    }
}
