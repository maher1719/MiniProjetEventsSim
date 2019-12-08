package nalouti.raoudha.monprojetevent;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("addUser.php")
    Call<APIResponse> addUserData(
            @Field("fullName") String fullName,
            @Field("email") String email,
            @Field("password") String password,
            @Field("urlimg") String urlimg

    );

    @FormUrlEncoded
    @POST("login.php")
    Call<APIResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("addEvent.php")
    Call<APIResponse> addEventData(
            @Field("titre") String titre,
            @Field("categorie") String categorie,
            @Field("type") String type,
            @Field("imgEvent") String imgEvent,
            @Field("montant") String montant,
            @Field("description") String description,
            @Field("capacite") String capacite,
            @Field("dateDebEvent") String dateDebEvent,
            @Field("timeDebEvent") String timeDebEvent,
            @Field("duree") int duree,
            @Field("dateFin") String dateFin,
            @Field("timeFinEvent") String timeFinEvent,
            @Field("lieuEvent") String lieuEvent,
            @Field("userMail") String userMail


    );
}
