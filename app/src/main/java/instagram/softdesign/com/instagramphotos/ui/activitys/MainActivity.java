package instagram.softdesign.com.instagramphotos.ui.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import instagram.softdesign.com.instagramphotos.ui.adapters.ProfilePhotoAdapter;
import instagram.softdesign.com.instagramphotos.R;
import instagram.softdesign.com.instagramphotos.data.managers.DataManager;
import instagram.softdesign.com.instagramphotos.data.network.restmodels.Datum;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    String access_token = "2268320038.ab103e5.6362c75a6bde464e8b96a0ce59064731";
    List<Datum> user_list = new ArrayList<>();

    @Bind(R.id.instagram_photos)
    RecyclerView photoView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Bind(R.id.etSearch)
    EditText searchField;
    @Bind(R.id.btnRegistration)
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Observable<String> editChanges = RxTextView.textChanges(searchField).debounce(500, TimeUnit.MILLISECONDS).map(String::valueOf);

        editChanges.subscribe(s -> {

            user_list.clear();
            getUserListInfo(s,access_token);

        });

        btnReg.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
            startActivity(intent);
        });

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
                        adapter = new ProfilePhotoAdapter(user_list);
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
