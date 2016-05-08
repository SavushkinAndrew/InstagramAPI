package instagram.softdesign.com.instagramphotos.data.managers;

import android.content.SharedPreferences;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import instagram.softdesign.com.instagramphotos.data.network.RestService;

import instagram.softdesign.com.instagramphotos.data.network.restmodels.Datum;
import retrofit.Callback;
import rx.Observable;

public class DataManager {
    private static DataManager INSTANCE = null;
    private RestService mRestService;
    private static SharedPreferences sPreferences;
    String TAG = getClass().getName();

    private DataManager() {
        mRestService = RestService.Factory.makeService();
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }


    public Observable<Datum> getUsers(String text, String access_token) {
        Log.e(TAG, "getUser");
        return mRestService.users(text,access_token).flatMap(jsonObject -> {

            Log.e("JSON",jsonObject.toString());

            List<Datum> user_list = new ArrayList<>();

            JsonArray jsonArray = jsonObject.getAsJsonArray("data");
            Log.e("DATA ARRAY",jsonArray.toString());

            JsonObject jsonObject3 = null;
            assert jsonArray != null;
            for (JsonElement element1 : jsonArray) {
                Datum user_ = new Datum();
                jsonObject3 = element1.getAsJsonObject();

                user_.setId(jsonObject3.get("id").getAsString());
                user_.setFullName(jsonObject3.get("full_name").getAsString());
                user_.setProfilePicture(jsonObject3.get("profile_picture").getAsString());
                user_.setUsername(jsonObject3.get("username").getAsString());

                Log.e("DataObject", jsonObject3.get("username").toString());

                user_list.add(user_);
            }

            return Observable.from(user_list);
        })
                .doOnCompleted(() -> {
                    Log.i(TAG, "on completed");
                })
                .onErrorResumeNext(throwable -> {
                    Log.i(TAG, "on error");
                    return Observable.empty();
                });
    }
}
