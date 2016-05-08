package instagram.softdesign.com.instagramphotos;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import instagram.softdesign.com.instagramphotos.data.managers.DataManager;
import instagram.softdesign.com.instagramphotos.data.network.restmodels.Datum;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    String access_token = "2268320038.ab103e5.6362c75a6bde464e8b96a0ce59064731";
    List<Datum> user_list = new ArrayList<>();

    RecyclerView photoView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Button mSearch;
    EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = (EditText)findViewById(R.id.editText);

        mSearch = (Button)findViewById(R.id.button);
        assert mSearch != null;
        mSearch.setOnClickListener(v -> {
            user_list.clear();
            getUserListInfo(searchField.getText().toString(),access_token);
        });



        photoView = (RecyclerView)findViewById(R.id.instagram_photos);
        layoutManager = new LinearLayoutManager(getApplication());
        photoView.setLayoutManager(layoutManager);
    }

    private void getUserListInfo(String text, String access_token) {
        DataManager.getInstance().getUsers(text, access_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Datum>() {
                    @Override
                    public void onCompleted() {
                        Log.e("COMPLETE", "complete");
                        adapter = new Adapter(user_list);
                        photoView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("ERROR", "error");
                    }

                    @Override
                    public void onNext(Datum user_) {
                        Log.e("NEXT", user_.getUsername() + " ");
                        user_list.add(user_);
                    }
                });
    }
}
