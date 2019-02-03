package co.com.apps4business.support;

import android.accounts.*;
import android.content.Context;
import android.os.Build;

import co.com.apps4business.BuildConfig;
import co.com.apps4business.utils.AppCacheUtils;
import timber.log.Timber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chvallej on 8/3/18.
 */

public class AppAccountManager {

    public static final String TAG = AppAccountManager.class.getSimpleName();
    public static final String KEY_ACCOUNT_TYPE = BuildConfig.APPLICATION_ID+".auth";
    public static final String KEY_USER_ACCOUNT_VERSION = "key_user_account_version";

    /**
     * Gets all the account related Odoo Auth
     *
     * @param context
     * @return List of AppUser instances if any
     */
    public static List<AppUser> getAllAccounts(Context context) {
        List<AppUser> users = new ArrayList<>();
        AccountManager aManager = AccountManager.get(context);
        for (Account account : aManager.getAccountsByType(KEY_ACCOUNT_TYPE)) {
            AppUser user = new AppUser();
            user.fillFromAccount(aManager, account);
            user.setAccount(account);
            users.add(user);
        }
        return users;
    }

    /**
     * Returns AppUser object with username
     *
     * @param context
     * @param username
     * @return instance for AppUser class or null
     */
    public static AppUser getDetails(Context context, String username) {
        for (AppUser user : getAllAccounts(context))
            if (user.getAndroidName().equals(username)) {
                return user;
            }
        return null;
    }

    /**
     * Login to user account. changes active state for user.
     * Other users will be automatically logged out
     *
     * @param context
     * @param username
     * @return new user object
     */
    public static AppUser login(Context context, String username) {

        AppUser activeUser = getActiveUser(context);
        // Logging out user if any
        if (activeUser != null) {
            logout(context, activeUser.getAndroidName());
        }

        AppUser newUser = getDetails(context, username);
        if (newUser != null) {
            AccountManager accountManager = AccountManager.get(context);
            accountManager.setUserData(newUser.getAccount(), "isactive", "true");
            Timber.i(newUser.getName() + " Logged in successfully");
            return newUser;
        }
        // Clearing old cache of the system
        AppCacheUtils.clearSystemCache(context);
        return null;
    }

    /**
     * Logout user
     *
     * @param context
     * @param username
     * @return true, if successfully logged out
     */
    public static boolean logout(Context context, String username) {
        AppUser user = getDetails(context, username);
        if (user != null) {
            AccountManager accountManager = AccountManager.get(context);
            accountManager.setUserData(user.getAccount(), "isactive", "false");
            Timber.i(user.getName() + " Logged out successfully");
            return true;
        }
        return false;
    }

    /**
     * Gets active user object
     *
     * @param context
     * @return user object (Instance of AppUser class)
     */
    public static AppUser getActiveUser(Context context) {
        for (AppUser user : getAllAccounts(context)) {
            if (user.isActive()) {
                return user;
            }
        }
        return null;
    }

    public static boolean isValidUserObj(Context context, AppUser user) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        int version = pref.getInt(userObjectKEY(user), 0);
        if (version == 0) {
            updateUserData(context, user, user);
            version = AppUser.USER_ACCOUNT_VERSION;
        }
        return (version == AppUser.USER_ACCOUNT_VERSION);
    }

    /**
     * Creates App account for app
     *
     * @param context
     * @param user    user instance (AppUser)
     * @return true, if account created successfully
     */

    public static boolean createAccount(Context context, AppUser user) {
        AccountManager accountManager = AccountManager.get(context);
        Account account = new Account(user.getAndroidName(), KEY_ACCOUNT_TYPE);
        if (accountManager.addAccountExplicitly(account, String.valueOf(user.getPassword()),
                user.getAsBundle())) {
            AppPreferenceManager pref = new AppPreferenceManager(context);
            if (pref.getInt(userObjectKEY(user), 0) != AppUser.USER_ACCOUNT_VERSION) {
                pref.putInt(userObjectKEY(user), AppUser.USER_ACCOUNT_VERSION);
            }
            return true;
        }
        return false;
    }

    public static String userObjectKEY(AppUser user) {
        return KEY_USER_ACCOUNT_VERSION + "_" + user.getAndroidName();
    }

    /**
     * Remove account from device
     *
     * @param context
     * @param username
     * @return true, if account removed successfully
     */
    public static boolean removeAccount(Context context, String username) {
        AppUser user = getDetails(context, username);
        if (user != null) {
            AccountManager accountManager = AccountManager.get(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (accountManager.removeAccountExplicitly(user.getAccount())) {
                    dropDatabase(user);
                }
                return true;
            } else {
                try {
                    AccountManagerFuture<Boolean> result = accountManager.
                            removeAccount(user.getAccount(), null, null);
                    if (result.getResult()) {
                        dropDatabase(user);
                    }
                    return true;
                } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void dropDatabase(AppUser user) {

    }

    public static AppUser updateUserData(Context context, AppUser user, AppUser newData) {
        if (user != null) {
            AccountManager accountManager = AccountManager.get(context);
            for (String key : newData.getAsBundle().keySet()) {
                accountManager.setUserData(user.getAccount(), key, newData.getAsBundle().get(key) + "");
            }
            AppPreferenceManager pref = new AppPreferenceManager(context);
            if (pref.getInt(userObjectKEY(user), 0) != AppUser.USER_ACCOUNT_VERSION) {
                pref.putInt(userObjectKEY(user), AppUser.USER_ACCOUNT_VERSION);
            }
        }
        return getDetails(context, newData.getAndroidName());
    }


}
