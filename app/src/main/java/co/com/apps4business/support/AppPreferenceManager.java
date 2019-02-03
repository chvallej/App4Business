package co.com.apps4business.support;

import android.content.Context;
import android.content.SharedPreferences;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppPreferenceManager {

    public static final String TAG = AppPreferenceManager.class.getSimpleName();
    private SharedPreferences mPref = null;

    public AppPreferenceManager(Context context) {
        mPref = android.preference.PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public boolean putStringSet(String key, List<String> values) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putStringSet(key, new HashSet<>(values));
        return editor.commit();
    }

    public List<String> getStringSet(String key) {
        List<String> list = new ArrayList<>();
        Set<String> vals = mPref.getStringSet(key, null);
        if (vals != null)
            list.addAll(vals);
        return list;
    }

    public String getString(String key, String default_value) {
        return mPref.getString(key, default_value);
    }

    public boolean putInt(String key, int value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public int getInt(String key, int default_value) {
        if (mPref.getAll().get(key) instanceof String) {
            try {
                return Integer.parseInt(mPref.getString(key, default_value + ""));
            } catch (NumberFormatException e) {
                Timber.e(e.getMessage());
                return default_value;
            }
        }
        return mPref.getInt(key, default_value);
    }

    public void setBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }
}
