package maleda.utility;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.URI;
import org.json.JSONObject;

public class APICall {

    public static String captureDate;
    public static String biometricData;
    public static String deviceId;

    public void Call() {
        HttpRequest request = HttpRequest.newBuilder()
                //.uri(URI.create("http://127.0.0.1:8000/capture"))
                .uri(URI.create("http://127.0.0.1:8000/maleda/capture"))
                //.header("X-RapidAPI-Host", "jokes-by-api-ninjas.p.rapidapi.com")
                //.header("X-RapidAPI-Key", "your-rapidapi-key")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            if (response != null) {
                JSONObject jsonObject = new JSONObject(response.body());

                biometricData = jsonObject.getString("biometricdata");
                deviceId = jsonObject.getString("deviceid");
                captureDate = jsonObject.getString("capturedate");

                //System.out.println(response.body());
            } else {
                System.out.println("the API resposes is null");
                AlertDefn.methodINFORMATION("The API response is NULL!!");
            }
        } catch (Exception e) {
            AlertDefn.methodERROR("The API service is not running");
        }

    }
}
