package co.com.apps4business.modules.security.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.com.apps4business.App;
import co.com.apps4business.R;
import co.com.apps4business.config.AppConstants;
import co.com.apps4business.config.DocumentType;
import co.com.apps4business.modules.partner.model.Partner;
import co.com.apps4business.modules.security.model.User;
import co.com.apps4business.rpc.AppInstance;
import co.com.apps4business.rpc.CreateAccountEventMessage;
import co.com.apps4business.utils.AppResource;
import co.com.apps4business.utils.AppStringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import timber.log.Timber;

import java.util.Calendar;

public class CreateAccount extends AppCompatActivity {

    private User mUser;
    private Partner mPartner;
    private EditText edtName, edtEmail, edtAddress, edtMobile, edtUserName, edtPassword, edtDocumentId, edtCity, edtDocumentType;
    private App mApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        init();
    }

    private void init() {
        Timber.v("Initialize variables");
        edtName = (EditText) findViewById(R.id.edtFullName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtDocumentId = (EditText) findViewById(R.id.edtDocumentId);
        edtCity = (EditText) findViewById(R.id.edtCity);
        edtDocumentType = (EditText) findViewById(R.id.edtDocumentType);
        mApp = (App) getApplicationContext();



        findViewById(R.id.btnCreateAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInput()){
                    if(mApp.inNetwork()) {
                        createAccount();
                    }else{
                        Toast.makeText( CreateAccount.this, AppResource.string(CreateAccount.this, R.string.toast_network_required), Toast.LENGTH_LONG)
                                .show();
                    }
                }

            }
        });

        findViewById(R.id.edtDocumentType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDocumentTypeDialog(v);
            }
        });

        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity();
            }
        });


    }

    private void createAccount() {
        UserCreator userCreator = new UserCreator();
        userCreator.execute();
    }

    private void fillEntities(){
        Calendar currentDay = Calendar.getInstance();
        mUser = new User();
        mPartner = new Partner();
        mUser.setName(edtName.getText().toString());
        mPartner.setName(edtName.getText().toString());
        mPartner.setEmail(edtEmail.getText().toString());
        mPartner.setStreet(edtAddress.getText().toString());
        mPartner.setMobile(edtMobile.getText().toString());
        mPartner.setCreateDate(currentDay.getTime());
        mPartner.setWriteDate(currentDay.getTime());
        mPartner.setActive(true);
        mPartner.setCountry("CO");
        mPartner.setCity(edtCity.getText().toString());
        mPartner.setCompany(false);
        mPartner.setDocumentType(edtDocumentType.getText().toString());
        mPartner.setDocumentId(edtDocumentId.getText().toString());
        mUser.setLogin(edtUserName.getText().toString());
        mUser.setPassword(edtPassword.getText().toString());
        mUser.setCreateDate(currentDay.getTime());
        mUser.setWriteDate(currentDay.getTime());
        //user.setUserType("User");
        mUser.setCustomer(true);
        mUser.setActive(true);
        mUser.setEmail(edtEmail.getText().toString());
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, Login.class));
        finish();
    }

    private void showDocumentTypeDialog(final View v) {
        final String[] documentTypes = AppStringUtils.getArray(DocumentType.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(AppResource.string(this, R.string.label_sign_up_document_type));
        builder.setSingleChoiceItems(documentTypes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((EditText) v).setText(documentTypes[i]);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private Boolean validateInput() {
        edtName.setError(null);
        edtEmail.setError(null);
        edtAddress.setError(null);
        edtCity.setError(null);
        edtMobile.setError(null);
        edtUserName.setError(null);
        edtPassword.setError(null);

        if (TextUtils.isEmpty(edtEmail.getText())) {
            edtEmail.setError(AppResource.string(this, R.string.error_provide_email));
            edtEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtUserName.getText())) {
            edtUserName.setError(AppResource.string(this, R.string.error_provide_username));
            edtUserName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtPassword.getText())) {
            edtPassword.setError(AppResource.string(this, R.string.error_provide_password));
            edtPassword.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtName.getText())) {
            edtName.setError(AppResource.string(this, R.string.error_provide_name));
            edtName.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(edtDocumentType.getText())) {
            edtDocumentType.setError(AppResource.string(this, R.string.error_provide_document_type));
            edtDocumentType.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtDocumentId.getText())) {
            edtDocumentId.setError(AppResource.string(this, R.string.error_provide_document_id));
            edtDocumentId.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtAddress.getText())) {
            edtAddress.setError(AppResource.string(this, R.string.error_provide_address));
            edtAddress.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtCity.getText())) {
            edtCity.setError(AppResource.string(this, R.string.error_provide_city));
            edtCity.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtMobile.getText())) {
            edtMobile.setError(AppResource.string(this, R.string.error_provide_mobile));
            edtMobile.requestFocus();
            return false;
        }




        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateAccountEvent(CreateAccountEventMessage createAccountEventMessage){
        Toast.makeText(CreateAccount.this, createAccountEventMessage.getMessage(), Toast.LENGTH_SHORT).show();
        startLoginActivity();
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

    private class UserCreator extends AsyncTask<Void, Void, Void> {
        private AppInstance appInstance;

        @Override
        protected Void doInBackground(Void... voids) {
            appInstance = AppInstance.createInstance(CreateAccount.this, AppConstants.URL_HOSTED_APP);
            fillEntities();
            appInstance.createAccount(mPartner, mUser);
            return null;
        }
    }
}
