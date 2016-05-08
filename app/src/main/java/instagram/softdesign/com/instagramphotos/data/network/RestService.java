package instagram.softdesign.com.instagramphotos.data.network;


import android.accounts.Account;
import android.content.res.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.RxJavaCallAdapterFactory;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import rx.android.BuildConfig;

public interface RestService {

    String BASE_URL = "https://api.instagram.com";

    @GET("v1/users/search?")
    Observable<JsonObject> users(@Query("q") String text,@Query("access_token") String access_token);

    class Factory {

        public static RestService makeService() {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.interceptors().add(new LoggingInterceptor());
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            okHttpClient.interceptors().add(logging);

            Gson gson = createGson();

            retrofit.Retrofit retrofit = new retrofit.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(RestService.class);
        }

        public static Gson createGson() {
            return new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                   // .registerTypeAdapter(User_.class, new UserSerializer())
                    .create();
        }

    }

}
