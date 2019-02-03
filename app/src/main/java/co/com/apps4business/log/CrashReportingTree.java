package co.com.apps4business.log;

import android.util.Log;
import com.crashlytics.android.Crashlytics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

public class CrashReportingTree extends Timber.Tree {
    private static final String CRASHLYTICS_KEY_PRIORITY = "priority";
    private static final String CRASHLYTICS_KEY_TAG = "tag";
    private static final String CRASHLYTICS_KEY_MESSAGE = "message";

    @Override
    protected void log(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable throwable) {

        if(priority == Log.ERROR || priority == Log.WARN){

            Throwable t = throwable != null
                    ? throwable
                    : new Exception(message);

            // Crashlytics
            Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority);
            Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag);
            Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message);
            Crashlytics.logException(t);

        }
    }
}
