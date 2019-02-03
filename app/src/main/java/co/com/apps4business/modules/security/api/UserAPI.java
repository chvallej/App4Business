package co.com.apps4business.modules.security.api;

import java.util.List;

import co.com.apps4business.modules.security.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {

    @GET("user")
    Call<List<User>> getAllUsers();

    @GET("user/{userId}")
    Call<User> getUser(@Path("userId") String userId);

    @GET("user/{login}/{password}")
    Call<User> authenticate(@Path("login") String login, @Path("password") String password);

    @POST("user")
    Call<User> createUser(@Body User user);
}
