package co.com.apps4business.rpc;

import co.com.apps4business.modules.security.model.User;
import retrofit2.Response;

public class LoginEventMessage {

    private Response<User> response;
    private String message;
    private String status;

    public LoginEventMessage(Response<User> response, String message, String status ){
        this.response = response;
        this.message = message;
        this.status = status;
    }

    public Response<User> getResponse() {
        return response;
    }

    public void setResponse(Response<User> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
