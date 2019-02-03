package co.com.apps4business.support;

import android.accounts.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import co.com.apps4business.modules.security.activity.Login;
import timber.log.Timber;

public class AppAuthenticator extends AbstractAccountAuthenticator {

    public static final String TAG = AppAuthenticator.class.getSimpleName();
    private Context mContext;
    public static final String KEY_NEW_ACCOUNT_REQUEST = "create_new_account";

    public AppAuthenticator(Context context){
        super(context);
        mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Timber.d("Start addAccount");

        final Intent intent = new Intent(mContext, Login.class);
        final Bundle result = new Bundle();

        intent.putExtra(KEY_NEW_ACCOUNT_REQUEST, true);
        result.putParcelable(AccountManager.KEY_INTENT, intent);

        return result;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}