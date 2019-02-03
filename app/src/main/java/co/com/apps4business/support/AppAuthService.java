package co.com.apps4business.support;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AppAuthService extends Service {
    private AppAuthenticator mAuthenticator;

    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = null;
        if (intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
            mAuthenticator = new AppAuthenticator(this);
            binder = mAuthenticator.getIBinder();
        }
        return binder;
    }
}