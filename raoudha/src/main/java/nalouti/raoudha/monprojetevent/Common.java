package nalouti.raoudha.monprojetevent;

public class Common {
    public static final String BASE_URL = "http://10.0.2.2:80/mesWebServices/";

    public static ApiInterface getAPI() {

        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
