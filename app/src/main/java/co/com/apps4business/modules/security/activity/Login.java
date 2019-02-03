package co.com.apps4business.modules.security.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;
import co.com.apps4business.App;
import co.com.apps4business.MainActivity;
import co.com.apps4business.R;
import co.com.apps4business.config.AppConstants;
import co.com.apps4business.database.AppDatabase;
import co.com.apps4business.modules.hr.dao.EmployeeDao;
import co.com.apps4business.modules.hr.model.Employee;
import co.com.apps4business.modules.partner.dao.PartnerDao;
import co.com.apps4business.modules.partner.model.Partner;
import co.com.apps4business.modules.security.dao.RoleDao;
import co.com.apps4business.modules.security.dao.UserDao;
import co.com.apps4business.modules.security.model.User;
import co.com.apps4business.rpc.AppInstance;
import co.com.apps4business.rpc.LoginEventMessage;
import co.com.apps4business.support.AppAccountManager;
import co.com.apps4business.support.AppUser;
import co.com.apps4business.utils.AppResource;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private AppInstance mAppInstance;
    private App mApp;
    // UI references
    private TextView mLoginProcessStatus;
    private EditText edtUsername, edtPassword;
    // Dao
    private UserDao userDao;
    private PartnerDao partnerDao;
    private EmployeeDao employeeDao;
    private RoleDao roleDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        mLoginProcessStatus =(TextView) findViewById(R.id.login_process_status);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.sign_up).setOnClickListener(this);
        edtUsername = (EditText) findViewById(R.id.login_user);
        edtPassword = (EditText) findViewById(R.id.login_password);

        //initialize daos
        userDao = AppDatabase.getDb(this).getUserDao();
        roleDao = AppDatabase.getDb(this).getRoleDao();
        partnerDao = AppDatabase.getDb(this).getPartnerDao();
        employeeDao = AppDatabase.getDb(this).getEmployeeDao();

        mApp = (App) getApplicationContext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if(mApp.inNetwork()) {
                    validateFields();

                }else{
                    Toast.makeText( this, AppResource.string(this, R.string.toast_network_required), Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case R.id.sign_up:
                startActivity(new Intent(this, CreateAccount.class));
                break;
        }
    }

    private void validateFields() {
        Timber.v("Start Validate Fields");

        edtUsername.setError(null);
        edtPassword.setError(null);
        if (TextUtils.isEmpty(edtUsername.getText())) {
            edtUsername.setError(AppResource.string(this, R.string.error_provide_username));
            edtUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(edtPassword.getText())) {
            edtPassword.setError(AppResource.string(this, R.string.error_provide_password));
            edtPassword.requestFocus();
            return;
        }


        findViewById(R.id.formLogin).setVisibility(View.GONE);
        findViewById(R.id.login_progress).setVisibility(View.VISIBLE);
        mLoginProcessStatus.setText(AppResource.string(this, R.string.status_connecting_to_server));

        mAppInstance = AppInstance.createInstance(Login.this, AppConstants.URL_HOSTED_APP);

        attemptLogin();
    }

    private void attemptLogin() {
        Timber.v("Start Attempt Login");
        final String username = edtUsername.getText().toString();
        final String password = edtPassword.getText().toString();
        mLoginProcessStatus.setText(AppResource.string(Login.this, R.string.status_logging_in));
        mAppInstance.authenticate(username, password);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEventMessage loginEventMessage){
        if(loginEventMessage.getStatus().equals("SUCCESS")){
            Timber.v("Login Success");
            User user = loginEventMessage.getResponse().body();
            final AppUser appUser = new AppUser();
            appUser.setUserName(user.getLogin());
            appUser.setName(user.getName());
            appUser.setPassword(user.getPassword());
            appUser.setUserId(user.getId());
            appUser.setRoleId(user.getRole().getId());

            if(user.getCustomer())
            {
                Partner partner = user.getPartner();
                if(!(userDao.userExist(user.getId()) >= 1)){
                    user.setPartnerId(partner.getId());
                    partner.setUserId(user.getId());
                    userDao.addUser(user);
                    partnerDao.addPartner(partner);
                    roleDao.addRole(user.getRole());

                    //Add admin user application
                    userDao.addUser(partner.getCreatedBy());
                    roleDao.addRole(partner.getCreatedBy().getRole());
                }
            }else{
                Employee employee = user.getEmployee();
                if(!(userDao.userExist(user.getId()) >= 1)){
                    user.setEmployee_id(employee.getId());
                    employee.setUserId(user.getId());
                    userDao.addUser(user);
                    roleDao.addRole(user.getRole());
                    employeeDao.addEmployee(employee);
                }
            }
            Toast.makeText(this, AppResource.string(this, R.string.toast_loggin_success), Toast.LENGTH_SHORT).show();
            mLoginProcessStatus.setText(AppResource.string(Login.this, R.string.status_login_success));

            AppUser appUserLogged = AppAccountManager.getDetails(Login.this, appUser.getAndroidName());
            AccountCreator accountCreator = new AccountCreator();
            if(appUserLogged == null){
                accountCreator.execute(appUser);
            }else {
                AppAccountManager.login(Login.this, appUser.getAndroidName());
                accountCreator.cancel(true);
            }

        }else{
            Timber.v("Login failed");

        }
    }

    private class AccountCreator extends AsyncTask<AppUser, Void, Boolean> {

        private AppUser mUser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoginProcessStatus.setText(AppResource.string(Login.this, R.string.status_creating_account));
        }

        @Override
        protected Boolean doInBackground(AppUser... params) {
            mUser = params[0];
            if (AppAccountManager.createAccount(Login.this, mUser)) {
                mUser = AppAccountManager.getDetails(Login.this, mUser.getAndroidName());
                AppAccountManager.login(Login.this, mUser.getAndroidName());

                return true;
            }

            return false;
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            startMainActivity();
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            mLoginProcessStatus.setText(AppResource.string(Login.this, R.string.status_redirecting));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    startMainActivity();


                }
            }, 1500);


        }
    }
}
