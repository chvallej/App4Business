package co.com.apps4business.modules.partner.api;

import co.com.apps4business.modules.partner.model.Partner;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface PartnerAPI {

    @GET("partner")
    Call<List<Partner>> getAllCustomers();

    @GET("partner/{partnerId}")
    Call<Partner> getPartner(@Path("partnerId") String partnerId);

    @POST("partner")
    Call<Partner> createPartner(@Body Partner partner);

    @GET("partner/documentId={documentId}")
    Call<Boolean> existPartnerByDocumentId(@Path("documentId") String documentId);
}
