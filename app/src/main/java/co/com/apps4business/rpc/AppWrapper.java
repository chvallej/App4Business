package co.com.apps4business.rpc;

import android.content.Context;
import co.com.apps4business.R;
import co.com.apps4business.modules.partner.api.PartnerAPI;
import co.com.apps4business.modules.partner.model.Partner;
import co.com.apps4business.modules.security.api.UserAPI;
import co.com.apps4business.modules.security.model.User;
import co.com.apps4business.utils.AppResource;
import co.com.apps4business.utils.GsonUtil;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import java.io.IOException;

public class AppWrapper<T> {
    protected String serverURL;
    protected Gson gson;
    protected Retrofit retrofit;
    protected Context mContext;

    public AppWrapper(Context context, String baseURL) {
        this.mContext = context;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        serverURL = baseURL;
        gson = GsonUtil.createGsonFromBuilder();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();


    }

    /**
     * Authenticate user
     *
     * @param username Username
     * @param password Password
     */
    public void authenticate(final String username, final String password) {


        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<User> call = userAPI.authenticate(username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()== null){
                    EventBus.getDefault().post(new LoginEventMessage(response, AppResource.string(mContext, R.string.error_login_failed), "FAILED"));
                }else{
                    EventBus.getDefault().post(new LoginEventMessage(response, AppResource.string(mContext, R.string.error_login_success), "SUCCESS"));
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                EventBus.getDefault().post(new LoginEventMessage( null, AppResource.string(mContext, R.string.error_login_failed), "FAILED"));
            }
        });

    }

    public void createAccount(Partner partner, User user){
        PartnerAPI partnerAPI = retrofit.create(PartnerAPI.class);
        Call<Partner> partnerCall = partnerAPI.createPartner(partner);
        Call<Boolean> validatePartnerCall = partnerAPI.existPartnerByDocumentId(partner.getDocumentId());
        UserAPI userAPI = retrofit.create(UserAPI.class);

        try {
            if(validatePartnerCall.execute().body()){
                EventBus.getDefault().post(new CreateAccountEventMessage(AppResource.string(mContext, R.string.toast_account_already_exist), "EXIST"));
            }
            Call<User> call = userAPI.getUser("1");
            User usersystem = call.execute().body();
            partner.setCreatedBy(usersystem);
            partner = partnerCall.execute().body();
            user.setPartner(partner);
            Call<User> userCall = userAPI.createUser(user);
            user = userCall.execute().body();
            EventBus.getDefault().post(new CreateAccountEventMessage(AppResource.string(mContext, R.string.toast_create_account_success), "OK"));
        } catch (IOException e) {
            Timber.e(e.getCause());
            EventBus.getDefault().post(new CreateAccountEventMessage(AppResource.string(mContext, R.string.toast_create_account_fail), "FAIL"));
        }


    }


}
