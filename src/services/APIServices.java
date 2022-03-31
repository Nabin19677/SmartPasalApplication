package services;

import java.io.IOException;

import application.Environment;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * This class is for client side restful api service
 * 
 * Singleton pattern is used for limiting to  only one instance of this class
 * 
 * @author anilk
 *
 */
public class APIServices {
	
	private static APIServices instance;
	
	private APIServices() {};
	
	public static APIServices getInstance() {
		 if(instance == null){
	            instance = new APIServices();
	        }
	        return instance;
	}
	
	private String URL = "http://localhost:5001";
	private String fourSquareURL = "https://api.foursquare.com/v2";
	
	private String fourSquareClientId = "FOQVLSNS2XD4XLON1YWOQRQAOXGAWKQUASFCHUTDWKZMZH1S";
	private String fourSquareClientSecret = "U1RMGTCN5HOMQTVCDVZTIHZZ2PCDTPUY3PXBFKYEYBUVVJW5";
	
	private OkHttpClient client = new OkHttpClient();
	
	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * Get near by venues of type department store, electronic store, food shop
     * @return String of JSON data including our venues 
     * @throws IOException
     */
    public String getNearByVenues() throws IOException {
    	System.out.println("Get Venues Called");
        Request request = new Request.Builder()
            .url(this.fourSquareURL+"/venues/search?"
            		+ "ll=27.693621,85.314905&categoryId=4bf58dd8d48988d1f6941735,4bf58dd8d48988d122951735,4bf58dd8d48988d1f9941735"
            		+ "&"
            		+ "client_id=" + this.fourSquareClientId
            		+ "&"
            		+ "client_secret=" + this.fourSquareClientSecret
            		+ "&"
            		+ "v=20191224")
            .build(); //category id belongs to department store, electronic store, and food shops
        	

        Response response = client.newCall(request).execute();
        return response.body().string();
      }
	
}
